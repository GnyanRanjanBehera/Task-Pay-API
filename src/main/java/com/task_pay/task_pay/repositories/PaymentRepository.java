package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByTask_TaskId(int taskId);

    Optional<Payment> findByOrderId(String orderId);

    Page<Objects[]> findBySenderUser_UserId(Integer userId, Pageable pageable);

}
