package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Objects;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query("SELECT t FROM Task t WHERE (t.senderUser.userId = :userId AND t.senderUserType = 'BUYER')")
    Page<Objects[]> findBuyerTasks(Integer userId, Pageable pageable);


    @Query("SELECT t FROM Task t WHERE (t.receiverUser.userId = :userId AND t.receiverUserType = 'SELLER')")
    Page<Objects[]> findSellerTasks(Integer userId, Pageable pageable);
}
