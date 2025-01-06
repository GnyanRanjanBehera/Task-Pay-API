package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.*;
import com.task_pay.task_pay.models.entities.MileStone;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.payloads.*;
import com.task_pay.task_pay.services.FCMService;
import com.task_pay.task_pay.services.FileService;
import com.task_pay.task_pay.services.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@Validated
@RequestMapping("/api/task")
public class TaskController {

    @Value("${task.image.path}")
    private  String taskImagePath;

    @Autowired
    private FileService fileService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FCMService fcmService;

    @PostMapping(value="/assignTask",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TaskDto> assignTask(
            @RequestParam(value = "senderUserId") Integer senderUserId,
            @RequestParam(value = "receiverUserId") Integer receiverUserId,
            @RequestParam(value = "taskName") String taskName,
            @RequestParam(value = "taskPrice") double taskPrice,
            @RequestParam(value = "taskAbout") String taskAbout,
            @RequestParam(value = "isFullPayment") String isFullPayment,
            @RequestPart(value = "file",required = false) List<MultipartFile> files,
            @RequestPart(value = "mileStones", required = false)  String mileStones
            ) throws IOException, ParseException {

        List<MileStoneDto> jsonMilestone = null;
        if (mileStones != null && !mileStones.isEmpty()) {
            jsonMilestone = taskService.getJson(mileStones);
        }
        TaskDto taskDto = taskService.assignTask(senderUserId,receiverUserId ,isFullPayment, taskName, taskPrice, taskAbout,files,jsonMilestone);
        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .title("Task Assigned")
                .topic("Task")
                .body("Task assigned by "+taskDto.getSenderUser().getName())
                .token(taskDto.getReceiverUser().getFcmToken())
                .build();
        try {
            if (notificationRequest.getToken() != null && !notificationRequest.getToken().isEmpty()) {
                fcmService.sendMessageToToken(notificationRequest);
            }
        }catch (InterruptedException | ExecutionException ignored){
           return  new ResponseEntity<>(taskDto,HttpStatus.OK);
        }
        return  new ResponseEntity<>(taskDto,HttpStatus.OK);
    }

    @PutMapping("/updateTask")
    public ResponseEntity<TaskDto> updateTask(@Validated @RequestBody UpdateTaskReq updateTaskReq){
        TaskDto taskDto = taskService.updateTask(updateTaskReq);
        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .title("Task Updated")
                .topic("Task")
                .body("Task updated by "+taskDto.getReceiverUser().getName())
                .token(taskDto.getSenderUser().getFcmToken())
                .build();
        try {
            if (notificationRequest.getToken() != null && !notificationRequest.getToken().isEmpty()) {
                fcmService.sendMessageToToken(notificationRequest);
            }
        }catch (InterruptedException | ExecutionException ignored){
            return new ResponseEntity<>(taskDto,HttpStatus.OK);
        }
        return new ResponseEntity<>(taskDto,HttpStatus.OK);
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<ApiMessageResponse> deleteTask(
            @RequestParam(value = "userId",required = true) Integer userId,
            @RequestParam(value = "taskId",required = true) Integer taskId
    ){
//        later we will  add notification
        taskService.deleteTask(userId,taskId);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("task deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }

    @PutMapping("/updateMilestone")
    public ResponseEntity<MileStoneDto> updateMilestone(@Valid @RequestBody UpdateMilestoneReq updateMilestoneReq){
        MileStoneDto mileStoneDto = taskService.updateMilestone(updateMilestoneReq);
        return new ResponseEntity<>(mileStoneDto,HttpStatus.OK);
    }

    @PostMapping("/addNewMilestone/{taskId}")
    public ResponseEntity<TaskDto> addNewMilestone(
            @Valid @RequestBody MileStoneDto mileStoneDto,
           @Valid @PathVariable(required = true) Integer userId){
        TaskDto taskDto = taskService.addNewMilestone(mileStoneDto, userId);
        return new ResponseEntity<>(taskDto,HttpStatus.OK);
    }

    @DeleteMapping("/deleteMilestone")
    public ResponseEntity<ApiMessageResponse> deleteMilestone(
            @RequestParam(value = "userId",required = true) Integer userId,
            @RequestParam(value = "mileStoneId",required = true) Integer mileStoneId
    ){
//        later we will  add notification
        taskService.deleteMilestone(userId,mileStoneId);
        ApiMessageResponse successfully = ApiMessageResponse.builder().message("Milestone delete deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(successfully,HttpStatus.OK);
    }


    @GetMapping("/fetchBuyerTasks/{userId}")
    public ResponseEntity<PageableResponse<TaskDto>> fetchBuyerTasks(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "taskStatus",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @PathVariable("userId") Integer userId
    ){
        PageableResponse<TaskDto> taskDtoPageableResponse = taskService.fetchBuyerTasks(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(taskDtoPageableResponse,HttpStatus.OK);
    }


    @GetMapping("/fetchTaskDetails")
    public ResponseEntity<TaskDto> fetchTaskDetails(
            @RequestParam(value = "taskId") int taskId,
            @RequestParam(value = "userId") int userId){
        TaskDto taskDto = taskService.fetchTaskDetails(userId, taskId);
        return new ResponseEntity<TaskDto>(taskDto,HttpStatus.OK);

    }

    @GetMapping("/fetchMilestoneByTaskId")
    public ResponseEntity<List<MileStoneDto>> fetchMilestoneByTaskId(
            @RequestParam(value = "taskId") int taskId,
            @RequestParam(value = "userId") int userId){
        List<MileStoneDto> mileStones = taskService.fetchMilestoneByTaskId(userId, taskId);
        return new ResponseEntity<>(mileStones,HttpStatus.OK);
    }


    @GetMapping("/fetchSellerTasks/{userId}")
    public ResponseEntity<PageableResponse<TaskDto>> fetchSellerTasks(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "taskStatus",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @PathVariable("userId") Integer userId
    ){
        PageableResponse<TaskDto> taskDtoPageableResponse = taskService.fetchSellerTasks(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(taskDtoPageableResponse,HttpStatus.OK);
    }

    @PostMapping("/acceptTask")
    public ResponseEntity<TaskDto> acceptTask(
          @RequestParam(value = "userId") Integer userId,
          @RequestParam(value = "taskId") Integer taskId
    ){
        TaskDto taskDto = taskService.acceptTask(userId, taskId);
        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .title("Task Accepted")
                .topic("Task")
                .body("Task accepted by "+taskDto.getReceiverUser().getName())
                .token(taskDto.getSenderUser().getFcmToken())
                .build();
        try {
            if (notificationRequest.getToken() != null && !notificationRequest.getToken().isEmpty()) {
                fcmService.sendMessageToToken(notificationRequest);
            }
        }catch (InterruptedException | ExecutionException ignored){
            return new ResponseEntity<>(taskDto,HttpStatus.OK);
        }
        return new ResponseEntity<>(taskDto,HttpStatus.OK);

    }

    @PostMapping("/declineTask")
    public ResponseEntity<TaskDto> declineTask(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "taskId") Integer taskId
    ){
        TaskDto taskDto = taskService.declineTask(userId, taskId);
        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .title("Task Declined")
                .body("Task declined by "+taskDto.getReceiverUser().getName())
                .topic("Task")
                .token(taskDto.getSenderUser().getFcmToken())
                .build();
        try {
            if (notificationRequest.getToken() != null && !notificationRequest.getToken().isEmpty()) {
                fcmService.sendMessageToToken(notificationRequest);
            }
        }catch (InterruptedException | ExecutionException ignored){
            return new ResponseEntity<>(taskDto,HttpStatus.OK);
        }
        return new ResponseEntity<>(taskDto,HttpStatus.OK);

    }

    @PutMapping("/reassignTask")
    public ResponseEntity<TaskDto> reassignTask(
            @RequestParam(required = true) Integer senderUserId,
            @RequestParam(required = true) Integer receiverUserId,
            @RequestParam(required = true) Integer taskId
    ){
        TaskDto taskDto = taskService.reassignTask(taskId, senderUserId, receiverUserId);

        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .title("Task Assigned")
                .topic("Task")
                .body("Task assigned by "+taskDto.getSenderUser().getName())
                .token(taskDto.getReceiverUser().getFcmToken())
                .build();
        try {
            if (notificationRequest.getToken() != null && !notificationRequest.getToken().isEmpty()) {
                fcmService.sendMessageToToken(notificationRequest);
            }
        }catch (InterruptedException | ExecutionException ignored){
            return  new ResponseEntity<>(taskDto,HttpStatus.OK);
        }
        return  new ResponseEntity<>(taskDto,HttpStatus.OK);

    }





    @GetMapping("/serveTaskImage/{taskId}/{imgId}")
    public void serverTaskImage(@PathVariable Integer taskId,@PathVariable Integer imgId,HttpServletResponse response) throws IOException {
        TaskDto taskDto = taskService.fetchTaskById(taskId);
        TaskFileDto taskFileDto = taskDto.getTaskFiles().stream().filter(file -> Objects.equals(file.getFileId(), imgId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("file not found with this id"));
        InputStream resource = fileService.getResource(taskImagePath,taskFileDto.getUrl());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
