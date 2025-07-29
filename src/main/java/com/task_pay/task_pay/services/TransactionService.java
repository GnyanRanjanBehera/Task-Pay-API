package com.task_pay.task_pay.services;


import com.task_pay.task_pay.models.dtos.PaymentDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.payloads.PageableResponse;

public interface TransactionService {
    PageableResponse<PaymentDto> findBuyerPayment(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<PaymentDto> findSellerPayment(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);

    PageableResponse<TaskDto> findBuyerPaymentReleasedTask(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<TaskDto> findSellerPaymentReleasedTask(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);
}
