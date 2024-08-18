package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.MileStonePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MileStonePaymentRepository extends JpaRepository<MileStonePayment,Integer> {
    Optional<MileStonePayment> findByMileStone_MileStoneId(int mileStoneId);
    Optional<MileStonePayment> findByOrderId(String orderId);
}
