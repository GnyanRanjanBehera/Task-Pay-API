package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.MileStonePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileStonePaymentRepository extends JpaRepository<MileStonePayment,Integer> {
}
