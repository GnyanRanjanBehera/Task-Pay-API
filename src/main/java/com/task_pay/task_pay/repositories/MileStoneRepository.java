package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileStoneRepository extends JpaRepository<MileStone,Integer> {
    List<MileStone> findByTaskTaskId(Integer taskId);


}
