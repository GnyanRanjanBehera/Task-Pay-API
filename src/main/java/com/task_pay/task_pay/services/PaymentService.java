package com.task_pay.task_pay.services;

import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.CheckOutOption;

public interface PaymentService {

    CheckOutOption releasePayment(Integer taskId,Integer senderUserId,Integer receiverUserId) throws RazorpayException;

    void releaseVerifyPayment(String paymentId,String orderId,String signature);


}
