package com.task_pay.task_pay.services.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.payloads.CheckOutOption;
import com.task_pay.task_pay.payloads.Prefill;
import com.task_pay.task_pay.repositories.TaskRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


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


    @Override
    public CheckOutOption releasePayment(Integer taskId,Integer senderUserId,Integer receiverUserId) throws RazorpayException {

        User senderUser = userRepository.findById(senderUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        User receiverUser = userRepository.findById(receiverUserId).orElseThrow(
                () -> new ResourceNotFoundException("Seller not found with this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with this id !"));
        Prefill prefillResponse=new Prefill();
        prefillResponse.setContact(senderUser.getMobileNumber());
        prefillResponse.setEmail(senderUser.getEmail());

        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",task.getTaskPrice()*1000);
        orderRequest.put("currency","INR");
        Order order = razorpayClient.orders.create(orderRequest);
        CheckOutOption checkOutOption=new CheckOutOption();
        checkOutOption.setAmount(order.get("amount"));
        checkOutOption.setKey(key);
        checkOutOption.setOrderId(order.get("id"));
        checkOutOption.setCurrency(order.get("currency"));
        checkOutOption.setPrefill(prefillResponse);
        return checkOutOption;
    }

    @Override
    public void releaseVerifyPayment(String paymentId,String orderId, String signature) {

    }
}
