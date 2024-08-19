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
        paymentService.verifyBlockPayment(paymentId,orderId,signature);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("Payment verify successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }

    @PostMapping("/blockMilestonePayment")
    public ResponseEntity<CheckOutOption> blockMilestonePayment(
            @RequestParam(value = "senderUserId") Integer senderUserId,
            @RequestParam(value = "receiverUserId") Integer receiverUserId,
            @RequestParam(value = "taskId") Integer taskId,
            @RequestParam(value = "milestoneId") Integer milestoneId
    ) throws RazorpayException {
        CheckOutOption checkOutOption = paymentService.blockMilestonePayment(taskId, milestoneId, senderUserId, receiverUserId);
        return new ResponseEntity<>(checkOutOption,HttpStatus.OK);

    }

    @PostMapping("/verifyBlockMileStonePayment")
    public ResponseEntity<ApiMessageResponse> verifyBlockMileStonePayment(
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "paymentId") String paymentId,
            @RequestParam(value = "signature") String signature
    ) throws RazorpayException {
        paymentService.verifyBlockMilestonePayment(paymentId,orderId,signature);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("Payment verify successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }

    @PostMapping("/releasedRequestPayment")
    public ResponseEntity<ApiMessageResponse> releasedRequestPayment(){
        return null;
    }

    @PostMapping("/releasedRequestMilestonePayment")
    public  ResponseEntity<ApiMessageResponse> releasedRequestMilestonePayment(){
        return null;
    }



    @PostMapping("/buyerReleasedPayment")
    public ResponseEntity<ApiMessageResponse> buyerReleasedPayment(){
        return null;
    }

    @PostMapping("/buyerReleasedMilestonePayment")
    public  ResponseEntity<ApiMessageResponse> buyerReleasedMilestonePayment(){
        return null;
    }

}
