package com.task_pay.task_pay.services;

import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.CheckOutOption;

public interface PaymentService {

    CheckOutOption blockPayment(Integer taskId,Integer senderUserId,Integer receiverUserId) throws RazorpayException;

    void verifyBlockPayment(String paymentId,String orderId,String signature) throws RazorpayException;


    CheckOutOption blockMilestonePayment(Integer taskId, Integer milestoneId,Integer senderUserId,Integer receiverUserId) throws RazorpayException;

    void verifyBlockMilestonePayment(String paymentId,String orderId,String signature) throws RazorpayException;

    void releasedRequestPayment(Integer senderId,Integer receiverId,Integer taskId);
    void releasedRequestMilestonePayment(Integer senderId,Integer receiverId,Integer taskId,Integer milestoneId);

    void buyerReleasedPayment(Integer senderId,Integer receiverId,Integer taskId);

    void buyerReleasedMilestonePayment(Integer senderId,Integer receiverId,Integer taskId,Integer milestoneId);

}
