package com.task_pay.task_pay.controllers;
import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.PaymentRequest;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.CheckOutOption;
import com.task_pay.task_pay.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/blockPayment")
    public ResponseEntity<ApiMessageResponse> blockPayment(){
        return null;
    }


    @PostMapping("/releasePaymentRequest")
    public ResponseEntity<ApiMessageResponse> releasePaymentRequest(){
        return null;
    }

    @PostMapping("/releasePayment")
    public ResponseEntity<CheckOutOption> releasePayment(
            @RequestParam(value = "senderUserId") Integer senderUserId,
            @RequestParam(value = "receiverUserId") Integer receiverUserId,
            @RequestParam(value = "taskId") Integer taskId
    ) throws RazorpayException {
        CheckOutOption paymentResponse = paymentService.releasePayment(taskId,senderUserId,receiverUserId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
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
