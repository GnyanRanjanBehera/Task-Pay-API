package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByTask_TaskId(int taskId);
    Payment findByOrderId(String orderId);

}
