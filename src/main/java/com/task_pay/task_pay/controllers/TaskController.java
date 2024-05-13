package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.dtos.TaskFileDto;
import com.task_pay.task_pay.services.TaskService;
import com.task_pay.task_pay.utils.request.AssignTaskRequest;
import com.task_pay.task_pay.utils.response.PageableResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @PostMapping( value = "/assignTask",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskDto> assignTask(
            @RequestParam(value = "senderUserId") Integer senderUserId,
            @RequestParam(value = "receiverUserId") Integer receiverUserId,
            @RequestParam(value = "taskName") String taskName,
            @RequestParam(value = "taskPrice") Integer taskPrice,
            @RequestParam(value = "taskAbout") String taskAbout,
            @RequestPart("file") MultipartFile[] files,
            @RequestBody(required = false)  List<MileStoneDto> mileStones
            ){
        System.out.println("=====image controller===="+files);
        System.out.println("=====milestone controller===="+mileStones);
//        TaskDto taskDto = taskService.assignTask(senderUserId,receiverUserId , taskName, taskPrice, taskAbout,files,mileStones);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(  value = "/assignTask1",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskDto> assignTask2(
            @ModelAttribute AssignTaskRequest request){
        TaskDto taskDto = taskService.assignTask1(request);
        return  new ResponseEntity<>(taskDto,HttpStatus.OK);
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
        return new ResponseEntity<>(taskDto,HttpStatus.OK);

    }
}
