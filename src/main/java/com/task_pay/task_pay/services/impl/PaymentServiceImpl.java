package com.task_pay.task_pay.services.impl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.entities.Payment;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.models.enums.Constant;
import com.task_pay.task_pay.models.enums.PaymentStatus;
import com.task_pay.task_pay.payloads.CheckOutOption;
import com.task_pay.task_pay.payloads.Prefill;
import com.task_pay.task_pay.repositories.PaymentRepository;
import com.task_pay.task_pay.repositories.TaskRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private PaymentRepository paymentRepository;



    @Override
    public CheckOutOption blockPayment(Integer taskId, Integer senderUserId, Integer receiverUserId)
            throws RazorpayException {
        User senderUser = userRepository.findById(senderUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        User receiverUser = userRepository.findById(receiverUserId).orElseThrow(
                () -> new ResourceNotFoundException("Seller not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with this id !"));
//        if you want handel logic here if task is status is Accepted
//        then block payment you can add also add logic here
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
        if(status){
            Payment payment = paymentRepository.findByOrderId(orderId);
            com.razorpay.Payment razorPay = razorpayClient.payments.fetch(paymentId);
            payment.setBlockedAt(new Date());
            payment.setStatus(PaymentStatus.BLOCKED);
            payment.setPaymentId(paymentId);
            payment.setMethod(razorPay.get("method"));
            paymentRepository.save(payment);
        }

    }

    public static String generateReceiptNumber() {
        final AtomicInteger sequenceNumber = new AtomicInteger(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        int sequence = sequenceNumber.incrementAndGet();
        return String.format("%s-%03d", dateStr, sequence);
    }
}
