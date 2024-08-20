package com.task_pay.task_pay.controllers;
import com.razorpay.RazorpayException;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.CheckOutOption;
import com.task_pay.task_pay.services.PaymentService;
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
    public ResponseEntity<ApiMessageResponse> releasedRequestPayment(
            @RequestParam(value ="senderId") Integer senderId,
            @RequestParam(value = "receiverId") Integer receiverId,
            @RequestParam(value = "taskId") Integer taskId
    ){
        paymentService.releasedRequestPayment(senderId,receiverId,taskId);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("request to released payment successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }

    @PostMapping("/releasedRequestMilestonePayment")
    public  ResponseEntity<ApiMessageResponse> releasedRequestMilestonePayment(
            @RequestParam(value ="senderId") Integer senderId,
            @RequestParam(value = "receiverId") Integer receiverId,
            @RequestParam(value = "taskId") Integer taskId,
            @RequestParam(value = "milestoneId") Integer milestoneId
    ){
        paymentService.releasedRequestMilestonePayment(senderId, receiverId, taskId, milestoneId);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("request to released payment successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }



    @PostMapping("/buyerReleasedPayment")
    public ResponseEntity<ApiMessageResponse> buyerReleasedPayment(
            @RequestParam(value ="senderId") Integer senderId,
            @RequestParam(value = "receiverId") Integer receiverId,
            @RequestParam(value = "taskId") Integer taskId
    ){
        paymentService.buyerReleasedPayment(senderId,receiverId,taskId);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("request to released payment successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }

    @PostMapping("/buyerReleasedMilestonePayment")
    public  ResponseEntity<ApiMessageResponse> buyerReleasedMilestonePayment(
            @RequestParam(value ="senderId") Integer senderId,
            @RequestParam(value = "receiverId") Integer receiverId,
            @RequestParam(value = "taskId") Integer taskId,
            @RequestParam(value = "milestoneId") Integer milestoneId
    ){
        paymentService.buyerReleasedMilestonePayment(senderId, receiverId, taskId, milestoneId);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("request to released payment successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }

}
