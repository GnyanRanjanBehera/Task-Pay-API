package com.task_pay.task_pay.services;

import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.request.PaymentRequest;
import com.task_pay.task_pay.payloads.request.PaymentVerifyRequest;
import com.task_pay.task_pay.payloads.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse releasePayment(PaymentRequest paymentRequest) throws RazorpayException;

    void releaseVerifyPayment(PaymentVerifyRequest paymentVerifyRequest);


}
