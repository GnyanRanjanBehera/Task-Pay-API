package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.dtos.TaskFileDto;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.services.FileService;
import com.task_pay.task_pay.services.InviteService;
import com.task_pay.task_pay.services.TaskService;
import com.task_pay.task_pay.services.UserService;
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
import java.io.IOException;
import java.io.InputStream;


@RestController
@Validated
@RequestMapping("/api/banker")
public class BankerController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.image.path}")
    private  String userImagePath;

    @Autowired
    private InviteService inviteService;

    @Value("${task.image.path}")
    private  String taskImagePath;

    @Autowired
    private TaskService taskService;
    @GetMapping("/fetchUserById{userId}")
    public ResponseEntity<UserDto> fetchUserById(@Valid @PathVariable("userId") Integer userId){
        UserDto userDto = userService.fetchUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/fetchUsers")
    public ResponseEntity<PageableResponse<UserDto>> fetchUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PageableResponse<UserDto> userDtoPageableResponse = userService.fetchUsers(pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(userDtoPageableResponse, HttpStatus.OK);
    }

    @GetMapping("/searchUser/{keywords}")
    public ResponseEntity<PageableResponse<UserDto>> searchUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @PathVariable("keywords") String keywords
    ){
        PageableResponse<UserDto> userDtoPageableResponse = userService.searchUser(keywords, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(userDtoPageableResponse, HttpStatus.OK);
    }

    @GetMapping("/serveUserImage/{userId}")
    public void serverUserImage(@PathVariable int userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.fetchUserById(userId);
        InputStream resource = fileService.getResource(userImagePath, user.getProfilePic());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


    @GetMapping("/fetchInvitedUsers/{userId}")
    public ResponseEntity<PageableResponse<InviteDto>> fetchInvitedUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "invitedAt",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @PathVariable("userId") Integer userId
    ){
        PageableResponse<InviteDto> inviteDtoPageableResponse = inviteService.fetchInvitedUsers(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(inviteDtoPageableResponse,HttpStatus.OK);
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
    @GetMapping("/serveTaskImage/{taskId}/{imgId}")
    public void serverTaskImage(@PathVariable int taskId,@PathVariable int imgId,HttpServletResponse response) throws IOException {
        TaskDto taskDto = taskService.fetchTaskById(taskId);
        TaskFileDto taskFileDto = taskDto.getTaskFiles().stream().filter(file -> file.getFileId() == imgId).findFirst().orElseThrow(() -> new ResourceNotFoundException("file not found with this id"));
        InputStream resource = fileService.getResource(taskImagePath,taskFileDto.getUrl());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
