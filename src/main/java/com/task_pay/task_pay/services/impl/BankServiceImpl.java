package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.BankDto;
import com.task_pay.task_pay.models.entities.Bank;
import com.task_pay.task_pay.repositories.BankRepository;
import com.task_pay.task_pay.services.BankService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public BankDto saveBank(Integer userId, BankDto bankDto) {
        BankDto build = BankDto.builder().bankName(bankDto.getBankName())
                .accNumber(bankDto.getAccNumber())
                .mobileNUmber(bankDto.getMobileNUmber())
                .accHolderName(bankDto.getAccHolderName())
                .IFSC(bankDto.getIFSC()).build();
        Bank bank = mapper.map(build, Bank.class);
        Bank saveBank = bankRepository.save(bank);
        return mapper.map(saveBank,BankDto.class);
    }

    @Override
    public BankDto fetchBank(Integer userId) {
        Optional<Bank> bank = bankRepository.findByUser_UserId(userId);
        return mapper.map(bank,BankDto.class);
    }

    @Override
    public BankDto updateBank(BankDto bankDto) {
        Bank bank = bankRepository.findById(bankDto.getBankId()).orElseThrow(() -> new ResourceNotFoundException("Bank not found with this id"));
        if(StringUtils.hasText(bankDto.getBankName())){
            bank.setBankName(bankDto.getBankName());
        }
        if(StringUtils.hasText(bankDto.getIFSC())){
            bank.setIFSC(bankDto.getIFSC());
        }
        if(StringUtils.hasText(bankDto.getAccNumber())){
            bank.setIFSC(bankDto.getAccNumber());
        }
        if(StringUtils.hasText(bankDto.getAccHolderName())){
            bank.setIFSC(bankDto.getAccHolderName());
        }
        if(StringUtils.hasText(bankDto.getMobileNUmber())){
            bank.setIFSC(bankDto.getMobileNUmber());
        }
        Bank saveBank = bankRepository.save(bank);

        return mapper.map(saveBank,BankDto.class);
    }

    @Override
    public void deleteBank(Integer bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new ResourceNotFoundException("Bank not found with this id"));
        bankRepository.delete(bank);
    }
}
