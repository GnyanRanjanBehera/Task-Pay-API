package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.services.TaskService;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/auth")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @PostMapping("/assignTask")
    public ResponseEntity<TaskDto> assignTask(
//            @RequestParam(value = "userId",required =true) Integer userId,
//            @RequestParam(value = "assignUserId",required =true) Integer assignUserId,
//            @RequestParam(value = "taskName") String taskName,
//            @RequestParam(value = "taskPrice") Integer taskPrice,
//            @RequestParam(value = "taskAbout") String taskAbout,
//            @RequestParam(value = "mileStones") List<Map<String, Object>> mileStones

            ){
        List<Map<String, Object>> milestonesData = new ArrayList<>();
        Map<String, Object> milestone = new HashMap<>();
        milestone.put("mileStoneName", "Milestone 1");
        milestone.put("mileStonePrice", 100);

        milestonesData.add(milestone);
        TaskDto taskDto = taskService.assignTask(1,2 , "taskName", 100, "taskAbout", milestonesData);
        return  new ResponseEntity<>(taskDto, HttpStatus.OK);
    }
}
