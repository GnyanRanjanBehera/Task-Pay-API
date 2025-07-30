package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.MileStonePaymentDto;
import com.task_pay.task_pay.models.dtos.PaymentDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.entities.MileStonePayment;
import com.task_pay.task_pay.models.entities.Payment;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.repositories.MileStonePaymentRepository;
import com.task_pay.task_pay.repositories.MileStoneRepository;
import com.task_pay.task_pay.repositories.PaymentRepository;
import com.task_pay.task_pay.repositories.TaskRepository;
import com.task_pay.task_pay.services.TransactionService;
import com.task_pay.task_pay.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MileStonePaymentRepository mileStonePaymentRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PageableResponse<PaymentDto> findBuyerPayment(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Payment> payment = paymentRepository.findBuyerPayment(userId, pageable);
        return Helper.getPageableResponse(payment, PaymentDto.class);
    }

    @Override
    public PageableResponse<PaymentDto> findSellerPayment(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Payment> payment = paymentRepository.findSellerPayment(userId, pageable);
        return Helper.getPageableResponse(payment, PaymentDto.class);
    }

    @Override
    public PageableResponse<TaskDto> findBuyerPaymentReleasedTask(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Task> task = taskRepository.findBuyerPaymentReleasedTask(userId, pageable);
        return Helper.getPageableResponse(task, TaskDto.class);
    }

    @Override
    public PageableResponse<TaskDto> findSellerPaymentReleasedTask(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Task> task = taskRepository.findSellerPaymentReleasedTask(userId, pageable);
        return Helper.getPageableResponse(task, TaskDto.class);
    }

    @Override
    public PaymentDto fetchTaskPaymentDetails(Integer taskId) {
        Payment sellerDidNotPayment = paymentRepository.findByTask_TaskId(taskId).orElseThrow(() -> new ResourceNotFoundException("Seller did not payment"));
        return mapper.map(sellerDidNotPayment, PaymentDto.class);
    }

    @Override
    public MileStonePaymentDto fetchMilestonePaymentDetails(Integer milestoneId) {
        MileStonePayment mileStonePayment = mileStonePaymentRepository.findByMileStone_MileStoneId(milestoneId).orElseThrow(() -> new ResourceNotFoundException("Seller did not payment"));
        return mapper.map(mileStonePayment,MileStonePaymentDto.class);
    }
}
