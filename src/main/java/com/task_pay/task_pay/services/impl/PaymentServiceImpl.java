package com.task_pay.task_pay.services.impl;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.request.PaymentRequest;
import com.task_pay.task_pay.payloads.request.PaymentVerifyRequest;
import com.task_pay.task_pay.payloads.response.PaymentResponse;
import com.task_pay.task_pay.services.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private  String key;

    @Value("${razorpay.key.secret}")
    private  String secret;
    @Override
    public PaymentResponse releasePayment(PaymentRequest paymentRequest) throws RazorpayException {
        int amount=100;
        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        PaymentResponse paymentResponse=new PaymentResponse();
        paymentResponse.put();


        razorpayClient.orders.create(paymentResponse);

        return null;
    }

    @Override
    public void releaseVerifyPayment(PaymentVerifyRequest paymentVerifyRequest) {

    }
}
