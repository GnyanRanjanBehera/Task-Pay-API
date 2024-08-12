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
    public ResponseEntity<CheckOutOption> blockPayment(
            @RequestParam(value = "senderUserId") Integer senderUserId,
            @RequestParam(value = "receiverUserId") Integer receiverUserId,
            @RequestParam(value = "taskId") Integer taskId
    ) throws RazorpayException {
        CheckOutOption checkOutOption = paymentService.blockPayment(taskId,senderUserId,receiverUserId);
        System.out.println(checkOutOption.getClass());
        return new ResponseEntity<>(checkOutOption, HttpStatus.OK);
    }

    @PostMapping("/verifyBlockPayment")
    public ResponseEntity<ApiMessageResponse> verifyBlockPayment(
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "paymentId") String paymentId,
            @RequestParam(value = "signature") String signature
    ) throws RazorpayException {
        paymentService.verifyBlockPayment(orderId,paymentId,signature);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("Payment verify successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }


    @PostMapping("/releasePaymentRequest")
    public ResponseEntity<ApiMessageResponse> releasePaymentRequest(){
        return null;
    }

    @PostMapping("/releasePayment")
    public ResponseEntity<CheckOutOption> releasePayment() {
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
