package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.entities.MileStone;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.models.entities.TaskFile;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.repositories.MileStoneRepository;
import com.task_pay.task_pay.repositories.TaskFileRepository;
import com.task_pay.task_pay.repositories.TaskRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MileStoneRepository mileStoneRepository;

    @Autowired
    private TaskFileRepository taskFileRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ModelMapper mapper;
    @Override
    public TaskDto assignTask(Integer userId , Integer assignUserId,
                              String taskName, Integer taskPrice,
                              String taskAbout,
                              List<Map<String, Object>> milestones) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));

        Task task = Task.builder()
                .assignUserId(assignUserId)
                .taskName(taskName)
                .taskPrice(taskPrice)
                .taskAbout(taskAbout)
                .user(user)
                .build();
        Task saveTask = taskRepository.save(task);
        for (Map<String, Object> milestone : milestones) {
            MileStone milestoneNew = new MileStone();
            milestoneNew.setMileStoneName((String) milestone.get("mileStoneName"));
            milestoneNew.setMileStonePrice(String.valueOf((Integer) milestone.get("mileStonePrice")));
            milestoneNew.setTask(saveTask);
            mileStoneRepository.save(milestoneNew);
        }
        return mapper.map(saveTask,TaskDto.class);
    }
}
