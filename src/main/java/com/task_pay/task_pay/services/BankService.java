package com.task_pay.task_pay.services;

import com.task_pay.task_pay.models.dtos.BankDto;

public interface BankService {
    BankDto saveBank(Integer userId,BankDto bankDto);
    BankDto fetchBank(Integer userId);
    BankDto updateBank( BankDto bankDto);
    void deleteBank(Integer bankId);

}
