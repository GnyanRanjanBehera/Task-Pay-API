package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.payloads.response.ApiMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiMessageResponse> releasePayment(){
        return null;
    }

    @PostMapping("/blockMilestonePayment")
    public ResponseEntity<ApiMessageResponse> blockMilestonePayment(){
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
