package com.task_pay.task_pay.controllers;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.response.ApiMessageResponse;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/blockPayment")
    public ResponseEntity<ApiMessageResponse> blockPayment(){
        return null;
    }


    @PostMapping("/releasePaymentRequest")
    public ResponseEntity<ApiMessageResponse> releasePaymentRequest(){
        return null;
    }

    @PostMapping("/releasePayment")
    @ResponseBody
    public ResponseEntity<ApiMessageResponse> releasePayment(@RequestBody Map<String,Object> data) throws RazorpayException {

        return null;
    }

    @PostMapping("/blockMilestonePayment")
    public ResponseEntity<ApiMessageResponse> blockMilestonePayment() throws RazorpayException {

        return null;
    }

    @PostMapping("/releaseMilestonePaymentRequest")
    public ResponseEntity<ApiMessageResponse> releaseMilestonePaymentRequest(){
        return null;
    }

    @PostMapping("/releaseMilestonePayment")
    public ResponseEntity<ApiMessageResponse> releaseMilestonePayment(){
        return null;
    }
}
