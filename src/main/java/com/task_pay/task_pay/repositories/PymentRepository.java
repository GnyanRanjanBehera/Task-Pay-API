package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PymentRepository extends JpaRepository<Payment,Integer> {
}
