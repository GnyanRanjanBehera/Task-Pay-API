package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query("SELECT t FROM Task t WHERE t.senderUser.userId = :userId AND t.senderUserType = 'BUYER'")
    Page<Task> findBuyerTasks(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.receiverUser.userId = :userId AND t.receiverUserType = 'SELLER'")
    Page<Task> findSellerTasks(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.senderUser.userId = :userId AND t.senderUserType = 'BUYER' AND t.taskStatus = 'RELEASEDREQUEST'")
    Page<Task> findBuyerPaymentReleasedTask(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.receiverUser.userId = :userId AND t.receiverUserType = 'BUYER' AND t.taskStatus = 'RELEASEDREQUEST'")
    Page<Task> findSellerPaymentReleasedTask(@Param("userId") Integer userId, Pageable pageable);
}
