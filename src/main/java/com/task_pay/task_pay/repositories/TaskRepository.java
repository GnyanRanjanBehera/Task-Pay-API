package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {


}
