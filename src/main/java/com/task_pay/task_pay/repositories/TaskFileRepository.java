package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.entities.TaskFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Objects;

public interface TaskFileRepository extends JpaRepository<TaskFile,Integer> {


}
