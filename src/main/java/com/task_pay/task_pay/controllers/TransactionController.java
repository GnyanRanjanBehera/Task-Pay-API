package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.MileStonePaymentDto;
import com.task_pay.task_pay.models.dtos.PaymentDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/findBuyerPayment")
    ResponseEntity<PageableResponse<PaymentDto>> findBuyerPayment(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
           @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
           @RequestParam(value = "sortBy",defaultValue = "taskStatus",required = false) String sortBy,
           @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
           @PathVariable("userId") Integer userId
    ){
        PageableResponse<PaymentDto> payment = transactionService.findBuyerPayment(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(payment, HttpStatus.OK);
    }



    @GetMapping("/findSellerPayment")
    ResponseEntity<PageableResponse<PaymentDto>> findSellerPayment(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
          @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
          @RequestParam(value = "sortBy",defaultValue = "taskStatus",required = false) String sortBy,
          @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
          @PathVariable("userId") Integer userId
    ){
        PageableResponse<PaymentDto> payment = transactionService.findSellerPayment(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/findBuyerPaymentReleasedTask")
    ResponseEntity<PageableResponse<TaskDto>> findBuyerPaymentReleasedTask(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
           @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
           @RequestParam(value = "sortBy",defaultValue = "taskStatus",required = false) String sortBy,
           @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
           @PathVariable("userId") Integer userId
    ){
        PageableResponse<TaskDto> task = transactionService.findBuyerPaymentReleasedTask(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(task, HttpStatus.OK);
    }




    @GetMapping("/findSellerPaymentReleasedTask")
    ResponseEntity<PageableResponse<TaskDto>> findSellerPaymentReleasedTask(
               @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
               @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
               @RequestParam(value = "sortBy",defaultValue = "taskStatus",required = false) String sortBy,
               @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
               @PathVariable("userId") Integer userId
    ){
        PageableResponse<TaskDto> task = transactionService.findSellerPaymentReleasedTask(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/fetchTaskPaymentDetails/{taskId}")
    ResponseEntity<PaymentDto> fetchTaskPaymentDetails(
          @PathVariable(value = "taskId") Integer taskId
    ){
        PaymentDto paymentDto = transactionService.fetchTaskPaymentDetails(taskId);
        return new ResponseEntity<>(paymentDto,HttpStatus.OK);


    }

    @GetMapping("/fetchMilestonePaymentDetails/{milestoneId}")
    ResponseEntity<MileStonePaymentDto> fetchMilestonePaymentDetails(
            @PathVariable(value = "milestoneId") Integer milestoneId
    ){
        MileStonePaymentDto mileStonePaymentDto = transactionService.fetchMilestonePaymentDetails(milestoneId);
        return new ResponseEntity<>(mileStonePaymentDto,HttpStatus.OK);


    }
}
