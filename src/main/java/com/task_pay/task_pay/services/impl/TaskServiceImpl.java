package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.dtos.UserDto;
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
import java.util.Objects;

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
                              List<MileStoneDto> mileStoneDtos) {
        User senderUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        User receiverUser = userRepository.findById(assignUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        if(Objects.equals(senderUser.getUserType(), "Buyer")){
            receiverUser.setUserType("Seller");
        }else{
            receiverUser.setUserType("Buyer");
        }
        User saveReceiverUser = userRepository.save(receiverUser);
        TaskDto taskDto = TaskDto.builder()
                .taskName(taskName)
                .taskPrice(taskPrice)
                .taskAbout(taskAbout)
                .taskStatus("Created")
                .senderUserType(senderUser.getUserType())
                .receiverUserType(saveReceiverUser.getUserType())
                .senderUser(mapper.map(senderUser, UserDto.class))
                .receiverUser(mapper.map(saveReceiverUser, UserDto.class))
                .build();
        Task saveTask = taskRepository.save(mapper.map(taskDto,Task.class));
        List<MileStone> milestones = new ArrayList<>();
        for (MileStoneDto milestoneDto : mileStoneDtos) {
            MileStone milestone = mapper.map(milestoneDto, MileStone.class);
            milestone.setTask(saveTask);
            milestones.add(milestone);
        }
        saveTask.setMileStones(milestones);
        taskRepository.save(saveTask);
        return mapper.map(saveTask,TaskDto.class);
    }
}
