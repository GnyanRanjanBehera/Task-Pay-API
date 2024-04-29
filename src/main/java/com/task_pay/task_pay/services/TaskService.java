package com.task_pay.task_pay.services;


import com.task_pay.task_pay.models.dtos.TaskDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TaskService {

    TaskDto assignTask( Integer userId, Integer assignUserId,
                       String taskName, Integer taskPrice,
                       String taskAbout, List<Map<String, Object>> milestones);

}
