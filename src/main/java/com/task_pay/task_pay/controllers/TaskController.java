package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.services.TaskService;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/assignTask")
    public ResponseEntity<TaskDto> assignTask(
            @RequestParam(value = "senderUserId") Integer senderUserId,
            @RequestParam(value = "receiverUserId") Integer receiverUserId,
            @RequestParam(value = "taskName") String taskName,
            @RequestParam(value = "taskPrice") Integer taskPrice,
            @RequestParam(value = "taskAbout") String taskAbout,
            @RequestBody  List<MileStoneDto> mileStoneDtos
            ){
        TaskDto taskDto = taskService.assignTask(senderUserId,receiverUserId , taskName, taskPrice, taskAbout,mileStoneDtos);
        return  new ResponseEntity<>(taskDto, HttpStatus.OK);
    }
}
