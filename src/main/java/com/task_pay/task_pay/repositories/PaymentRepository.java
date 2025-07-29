package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Objects;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByTask_TaskId(int taskId);

    Optional<Payment> findByOrderId(String orderId);

    Page<Objects[]> findBySenderUser_UserId(Integer userId, Pageable pageable);

    @Query("SELECT p FROM Payment p " +
            "WHERE p.senderUser.userId = :userId " +
            "AND p.senderUser.userType = 'BUYER' " +
            "AND p.status = 'SUCCESS'")
    Page<Payment> findBuyerPayment(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT p FROM Payment p " +
            "WHERE p.receiverUser.userId = :userId " +
            "AND p.receiverUser.userType = 'SELLER' " +
            "AND p.status = 'SUCCESS'")
    Page<Payment> findSellerPayment(@Param("userId") Integer userId, Pageable pageable);

}
