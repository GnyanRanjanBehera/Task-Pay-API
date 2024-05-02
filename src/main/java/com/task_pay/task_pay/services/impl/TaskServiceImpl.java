package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.dtos.TaskFileDto;
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
import com.task_pay.task_pay.utils.Helper;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

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
                              String taskAbout,List<MultipartFile> images,
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
                .createAt(new Date())
                .senderUser(mapper.map(senderUser, UserDto.class))
                .receiverUser(mapper.map(saveReceiverUser, UserDto.class))
                .build();
        Task saveTask = taskRepository.save(mapper.map(taskDto,Task.class));

        List<TaskFile> taskFiles = new ArrayList<>();
        for (MultipartFile  file:images){
            TaskFile taskFile = new TaskFile();
            taskFile.setTask(saveTask);
            taskFile.setUrl(file.toString());
            taskFiles.add(taskFile);

        }

        List<MileStone> milestones = new ArrayList<>();
        for (MileStoneDto milestoneDto : mileStoneDtos) {
            MileStone milestone = mapper.map(milestoneDto, MileStone.class);
            milestone.setTask(saveTask);
            milestones.add(milestone);
        }
        saveTask.setTaskFiles(taskFiles);
        saveTask.setMileStones(milestones);
        taskRepository.save(saveTask);
        return mapper.map(saveTask,TaskDto.class);
    }

    @Override
    public PageableResponse<TaskDto> fetchBuyerTasks(Integer userId,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Objects[]> buyerTasks = taskRepository.findBuyerTasks(userId, pageable);
        return Helper.getPageableResponse(buyerTasks, TaskDto.class);
    }

    @Override
    public PageableResponse<TaskDto> fetchSellerTasks(Integer userId,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Objects[]> buyerTasks = taskRepository.findSellerTasks(userId, pageable);
        return Helper.getPageableResponse(buyerTasks, TaskDto.class);
    }

    @Override
    public TaskDto acceptTask(Integer userId,Integer taskId) {
       userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this userId !"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with this taskId !"));
        task.setTaskStatus("Accepted");
        Task saveTask = taskRepository.save(task);
        return mapper.map(saveTask, TaskDto.class);
    }
}
