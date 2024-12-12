package com.task_pay.task_pay.services.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.MileStone;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.models.entities.TaskFile;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.models.enums.MilestoneStatus;
import com.task_pay.task_pay.models.enums.TaskStatus;
import com.task_pay.task_pay.models.enums.UserType;
import com.task_pay.task_pay.payloads.UpdateTaskReq;
import com.task_pay.task_pay.repositories.MileStoneRepository;
import com.task_pay.task_pay.repositories.TaskFileRepository;
import com.task_pay.task_pay.repositories.TaskRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.FCMService;
import com.task_pay.task_pay.services.FileService;
import com.task_pay.task_pay.services.TaskService;
import com.task_pay.task_pay.utils.Helper;
import com.task_pay.task_pay.payloads.PageableResponse;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Value("${task.image.path}")
    private  String taskImagePath;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MileStoneRepository mileStoneRepository;

    @Autowired
    private TaskFileRepository taskFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FCMService fcmService;

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Override
    public TaskDto assignTask(Integer senderUserId , Integer receiverUserId,String isFullPayment,
                              String taskName, double taskPrice,
                              String taskAbout, List<MultipartFile> images,
                              List<MileStoneDto> mileStoneDtos) throws IOException, ParseException {

        User senderUser = userRepository.findById(senderUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));
        User receiverUser = userRepository.findById(receiverUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this id !"));


        if(Objects.equals(senderUser.getUserType(), UserType.BUYER)){
            receiverUser.setUserType(UserType.SELLER);
        }else{
            receiverUser.setUserType(UserType.BUYER);
        }
        User saveReceiverUser = userRepository.save(receiverUser);

        Task task = Task.builder()
                .taskName(taskName)
                .createdAt(new Date())
                .taskPrice(taskPrice)
                .isFullPayment(isFullPayment)
                .taskAbout(taskAbout)
                .taskStatus(TaskStatus.CREATED)
                .senderUserType(senderUser.getUserType())
                .receiverUserType(saveReceiverUser.getUserType())
                .senderUser(senderUser)
                .receiverUser(saveReceiverUser)
                .build();

        System.out.println("task after creating obejct==="+task);
        Task saveTask = taskRepository.save(task);
        System.out.println("saveTask==="+saveTask);

        List<TaskFile> taskFiles = new ArrayList<>();
        if(images!=null){
            for (MultipartFile  file:images){
                TaskFile taskFile = new TaskFile();
                taskFile.setTask(saveTask);
                taskFile.setUrl(fileService.uploadImage(file, taskImagePath));
                taskFiles.add(taskFile);
            }
        }
        saveTask.setTaskFiles(taskFiles);
        List<MileStone> milestones = new ArrayList<>();
        if(mileStoneDtos!=null) {
            for (MileStoneDto milestoneDto : mileStoneDtos) {
                MileStone mileStone = getMileStone(milestoneDto, saveTask);
                milestones.add(mileStone);
            }
        }
        saveTask.setMileStones(milestones);

        Task save = taskRepository.save(saveTask);
        return mapper.map(save,TaskDto.class);
    }

    @Override
    public TaskDto updateTask(UpdateTaskReq updateTaskReq) {
        User user = userRepository.findById(updateTaskReq.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found by this id !"));
        Task task = taskRepository.findById(updateTaskReq.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task not found by this id"));
        List<MileStone> mileStones = task.getMileStones();
        double totalMisPrice=0.0;
        for (MileStone m:mileStones){
            totalMisPrice+=m.getMileStonePrice();
        }
//        also calculate milestone price total to to check updated price is grater than milestone then update the task
        task.setTaskName(updateTaskReq.getTaskName());
        if(StringUtils.hasText(updateTaskReq.getTaskName())){
            task.setTaskName(updateTaskReq.getTaskName());
        }
        if(StringUtils.hasText(updateTaskReq.getTaskAbout())){
            task.setTaskAbout(updateTaskReq.getTaskAbout());
        }
        if(updateTaskReq.getTaskPrice() >= task.getTaskPrice()){
            task.setTaskPrice(updateTaskReq.getTaskPrice());
        }else{
            throw new ResourceNotFoundException("Your enter amount");
        }
        Task saveTask = taskRepository.save(task);
        return mapper.map(saveTask,TaskDto.class);
    }


    @Override
    public void deleteTask(Integer userId, Integer taskId) {
        userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found by this id !"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("task not found by this id"));
        if(task.getTaskStatus()==TaskStatus.CREATED || task.getTaskStatus()==TaskStatus.DECLINED){
            taskRepository.delete(task);
        }
    }


    @NotNull
    private static MileStone getMileStone(MileStoneDto milestoneDto, Task saveTask) {
        MileStone mileStone=new MileStone();
        mileStone.setMileStoneName(milestoneDto.getMileStoneName());
        mileStone.setMileStonePrice(milestoneDto.getMileStonePrice());
        mileStone.setMilestoneStatus(MilestoneStatus.CREATED);
        mileStone.setCreatedAt(new Date());
        if(milestoneDto.getStartDate() !=null &&
                milestoneDto.getEndDate()!=null
        ){
                mileStone.setStartDate(milestoneDto.getStartDate());
                mileStone.setEndDate(milestoneDto.getEndDate());
        }
        mileStone.setTask(saveTask);
        return mileStone;
    }

    @Override
    public List<MileStoneDto> getJson(String milestones)throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(milestones, new TypeReference<List<MileStoneDto>>() {});
    }

    @Override
    public TaskDto fetchTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id !"));
        return mapper.map(task,TaskDto.class);
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
        Page<Objects[]> sellerTasks = taskRepository.findSellerTasks(userId, pageable);
        return Helper.getPageableResponse(sellerTasks, TaskDto.class);
    }

    @Override
    public TaskDto acceptTask(Integer userId,Integer taskId) {
       userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this userId !"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with this taskId !"));
        task.setTaskStatus(TaskStatus.ACCEPTED);
        task.setAcceptedAt(new Date());
        Task saveTask = taskRepository.save(task);
        return mapper.map(saveTask, TaskDto.class);
    }

    @Override
    public TaskDto declineTask(Integer userId, Integer taskId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this userId !"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with this taskId !"));
        task.setTaskStatus(TaskStatus.DECLINED);
        task.setDeclinedAt(new Date());
        Task saveTask = taskRepository.save(task);
        return mapper.map(saveTask, TaskDto.class);
    }
}
