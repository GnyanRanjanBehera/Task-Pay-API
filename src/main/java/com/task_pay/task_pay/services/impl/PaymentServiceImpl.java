package com.task_pay.task_pay.services.impl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.PaymentDto;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.*;
import com.task_pay.task_pay.models.enums.*;
import com.task_pay.task_pay.payloads.CheckOutOption;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.payloads.Prefill;
import com.task_pay.task_pay.repositories.*;
import com.task_pay.task_pay.services.PaymentService;
import com.task_pay.task_pay.utils.Helper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private  String key;

    @Value("${razorpay.key.secret}")
    private  String secret;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MileStoneRepository mileStoneRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MileStonePaymentRepository mileStonePaymentRepository;



    @Override
    public CheckOutOption blockPayment(Integer taskId, Integer senderUserId, Integer receiverUserId)
            throws RazorpayException {
        User senderUser = userRepository.findById(senderUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        User receiverUser = userRepository.findById(receiverUserId).orElseThrow(
                () -> new ResourceNotFoundException("Seller not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with this id !"));

        if(task.getTaskStatus()==TaskStatus.BLOCKED){
            throw new ResourceNotFoundException("Payment is already blocked");
        }

        if(task.getTaskStatus()!=TaskStatus.ACCEPTED){
            throw  new ResourceNotFoundException("The task is not accepted by seller !");
        }

        Prefill prefill=new Prefill();
        prefill.setName(senderUser.getName());
        prefill.setContact(senderUser.getMobileNumber());
        prefill.setEmail(senderUser.getEmail());

        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",task.getTaskPrice()*1000);
        orderRequest.put("currency",Constant.INR.name());
        orderRequest.put("receipt",generateReceiptNumber());
        Order order = razorpayClient.orders.create(orderRequest);
        CheckOutOption checkOutOption=new CheckOutOption();
        checkOutOption.setName(Constant.TaskPay.name());
        checkOutOption.setDescription(task.getTaskName());
        checkOutOption.setAmount(order.get("amount"));
        checkOutOption.setKey(key);
        checkOutOption.setOrderId(order.get("id"));
        checkOutOption.setCurrency(order.get("currency"));
        checkOutOption.setCreatedAt(order.get("created_at"));
        checkOutOption.setPrefill(prefill);
        Optional<Payment> existPayment = paymentRepository.findByTask_TaskId(taskId);
        Payment payment;
        if(existPayment.isPresent()){
             payment = existPayment.get();
            payment.setOrderId(checkOutOption.getOrderId());
            payment.setAmount(checkOutOption.getAmount());
            payment.setCreatedAt(checkOutOption.getCreatedAt());
            paymentRepository.save(payment);
        }else{
            payment = new Payment();
            payment.setOrderId(checkOutOption.getOrderId());
            payment.setAmount(checkOutOption.getAmount());
            payment.setCreatedAt(checkOutOption.getCreatedAt());
            payment.setStatus(PaymentStatus.CREATED);
            payment.setReceipt(order.get("receipt"));
            payment.setTask(task);
            payment.setSenderUser(senderUser);
            payment.setReceiverUser(receiverUser);
            paymentRepository.save(payment);
        }
        return checkOutOption;
    }

    @Override
    public void verifyBlockPayment(String paymentId, String orderId, String signature) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id",orderId);
        options.put("razorpay_payment_id",paymentId);
        options.put("razorpay_signature", signature);
        boolean status =  Utils.verifyPaymentSignature(options, secret);

        Payment  payment = paymentRepository.findByOrderId(orderId).orElseThrow(()->new ResourceNotFoundException("order id not found"));
        com.razorpay.Payment razorPay = razorpayClient.payments.fetch(paymentId);
        if(status){
            Task task = payment.getTask();
            task.setTaskStatus(TaskStatus.BLOCKED);
            task.setBlockedAt(new Date());
            payment.setSuccessAt(new Date());
            payment.setStatus(PaymentStatus.SUCCESS);
        }else{
            payment.setProcessingAt(new Date());
            payment.setStatus(PaymentStatus.PROCESSING);
        }
        payment.setPaymentId(paymentId);
        payment.setMethod(razorPay.get("method"));
        paymentRepository.save(payment);

    }




    @Override
    public CheckOutOption blockMilestonePayment(Integer taskId, Integer milestoneId, Integer senderUserId, Integer receiverUserId)
            throws RazorpayException {
        User senderUser = userRepository.findById(senderUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        User receiverUser = userRepository.findById(receiverUserId).orElseThrow(
                () -> new ResourceNotFoundException("Seller not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with this id !"));

        if(task.getTaskStatus()!=TaskStatus.ACCEPTED){
            throw  new ResourceNotFoundException("The task is not accepted by seller !");
        }
        MileStone mileStone = mileStoneRepository.findById(milestoneId).orElseThrow(() -> new ResourceNotFoundException("Milestone not found with id !"));
        if(mileStone.getMilestoneStatus()==MilestoneStatus.BLOCKED){
            throw  new ResourceNotFoundException("This milestone payment already done !");
        }

        Prefill prefill=new Prefill();
        prefill.setName(senderUser.getName());
        prefill.setContact(senderUser.getMobileNumber());
        prefill.setEmail(senderUser.getEmail());

        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",mileStone.getMileStonePrice()*1000);
        orderRequest.put("currency",Constant.INR.name());
        orderRequest.put("receipt",generateReceiptNumber());
        Order order = razorpayClient.orders.create(orderRequest);


        CheckOutOption checkOutOption=new CheckOutOption();
        checkOutOption.setName(Constant.TaskPay.name());
        checkOutOption.setDescription(mileStone.getMileStoneName());
        checkOutOption.setAmount(order.get("amount"));
        checkOutOption.setKey(key);
        checkOutOption.setOrderId(order.get("id"));
        checkOutOption.setCurrency(order.get("currency"));
        checkOutOption.setCreatedAt(order.get("created_at"));
        checkOutOption.setPrefill(prefill);
        Optional<MileStonePayment> existPayment = mileStonePaymentRepository.findByMileStone_MileStoneId(milestoneId);
        MileStonePayment mileStonePayment;
        if(existPayment.isPresent()){
            mileStonePayment = existPayment.get();
            mileStonePayment.setOrderId(checkOutOption.getOrderId());
            mileStonePayment.setAmount(checkOutOption.getAmount());
            mileStonePayment.setCreatedAt(checkOutOption.getCreatedAt());
            mileStonePaymentRepository.save(mileStonePayment);
        }else{
            mileStonePayment = new MileStonePayment();
            mileStonePayment.setOrderId(checkOutOption.getOrderId());
            mileStonePayment.setAmount(checkOutOption.getAmount());
            mileStonePayment.setCreatedAt(checkOutOption.getCreatedAt());
            mileStonePayment.setStatus(PaymentStatus.CREATED);
            mileStonePayment.setReceipt(order.get("receipt"));
            mileStonePayment.setMileStone(mileStone);
            mileStonePayment.setSenderUser(senderUser);
            mileStonePayment.setReceiverUser(receiverUser);
            mileStonePaymentRepository.save(mileStonePayment);
        }
        return checkOutOption;
    }

    @Override
    public void verifyBlockMilestonePayment(String paymentId, String orderId, String signature)
            throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id",orderId);
        options.put("razorpay_payment_id",paymentId);
        options.put("razorpay_signature", signature);
        boolean status =  Utils.verifyPaymentSignature(options, secret);

        MileStonePayment  mileStonePayment = mileStonePaymentRepository.findByOrderId(orderId).orElseThrow(()->new ResourceNotFoundException("order id not found"));
        com.razorpay.Payment razorPay = razorpayClient.payments.fetch(paymentId);
        if(status){
            MileStone mileStone = mileStonePayment.getMileStone();
            mileStone.setMilestoneStatus(MilestoneStatus.BLOCKED);
            mileStone.setBlockedAt(new Date());
            mileStonePayment.setSuccessAt(new Date());
            mileStonePayment.setStatus(PaymentStatus.SUCCESS);
        }else{
            mileStonePayment.setProcessingAt(new Date());
            mileStonePayment.setStatus(PaymentStatus.PROCESSING);
        }
        mileStonePayment.setPaymentId(paymentId);
        mileStonePayment.setMethod(razorPay.get("method"));
        mileStonePaymentRepository.save(mileStonePayment);

    }

    @Override
    public void releasedRequestPayment(Integer senderId, Integer receiverId, Integer taskId) {
        userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id !"));
        if(task.getTaskStatus()== TaskStatus.BLOCKED && task.getReceiverUserType()== UserType.SELLER){
            task.setTaskStatus(TaskStatus.RELEASEDREQUEST);
            taskRepository.save(task);
        }

    }

    @Override
    public void releasedRequestMilestonePayment(Integer senderId, Integer receiverId, Integer taskId, Integer milestoneId) {
        userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id !"));
        MileStone mileStone = mileStoneRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Milestone not found with this id !"));
        if(task.getTaskStatus()== TaskStatus.BLOCKED && task.getReceiverUserType()== UserType.SELLER){
            mileStone.setMilestoneStatus(MilestoneStatus.RELEASEDREQUEST);
            mileStoneRepository.save(mileStone);
        }
    }

    @Override
    public void buyerReleasedPayment(Integer senderId, Integer receiverId, Integer taskId) {
        userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id !"));
        if(task.getTaskStatus()==TaskStatus.RELEASEDREQUEST && task.getSenderUserType()==UserType.BUYER){
            task.setTaskStatus(TaskStatus.RELEASED);
            taskRepository.save(task);
        }

    }

    @Override
    public void buyerReleasedMilestonePayment(Integer senderId, Integer receiverId, Integer taskId, Integer milestoneId) {
        userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id !"));
        MileStone mileStone = mileStoneRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Milestone not found with this id !"));
        if(mileStone.getMilestoneStatus()==MilestoneStatus.RELEASEDREQUEST && task.getSenderUserType()==UserType.BUYER){
            mileStone.setMilestoneStatus(MilestoneStatus.RELEASED);
            mileStoneRepository.save(mileStone);
        }

    }

    @Override
    public PageableResponse<PaymentDto> fetchSenderPayment(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Objects[]> payment = paymentRepository.findBySenderUser_UserId(userId,pageable);
        return Helper.getPageableResponse(payment, PaymentDto.class);
    }

    public static String generateReceiptNumber() {
        final AtomicInteger sequenceNumber = new AtomicInteger(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        int sequence = sequenceNumber.incrementAndGet();
        return String.format("%s-%03d", dateStr, sequence);
    }
}
