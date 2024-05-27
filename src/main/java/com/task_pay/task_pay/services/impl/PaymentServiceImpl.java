package com.task_pay.task_pay.services.impl;
import com.phonepe.sdk.pg.Env;
import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
import com.phonepe.sdk.pg.payments.v1.models.request.PgPayRequest;
import com.task_pay.task_pay.services.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public void blockPayment() {
        String merchantId = "merchantId";
        String saltKey = "saltKey";
        Env env = Env.UAT;
        boolean shouldPublishEvents = true;
        PhonePePaymentClient phonepeClient = new PhonePePaymentClient(merchantId, saltKey, 0,env, shouldPublishEvents);

//        PgPayRequest pgPayRequest= PgPayRequest.PayPagePayRequestBuilder()
//                .amount(amount)
//                .merchantId(merchantId)
//                .merchantTransactionId(merchantTransactionId)
//                .callbackUrl(callbackurl)
//                .merchantUserId(merchantUserId)
//                .redirectUrl(redirecturl)
//                .redirectMode(redirectMode)
//                .build();


    }
}
