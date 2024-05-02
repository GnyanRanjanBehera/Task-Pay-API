package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.services.UserService;
import com.task_pay.task_pay.utils.request.SendOtpRequest;
import com.task_pay.task_pay.utils.response.ApiMessageResponse;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
import com.task_pay.task_pay.utils.response.PageableResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/fetchUserById{userId}")
    public  ResponseEntity<UserDto> fetchUserById(@Valid @PathVariable("userId") Integer userId){
        UserDto userDto = userService.fetchUserById(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @PutMapping("/updateUserType")
    public  ResponseEntity<AuthenticationResponse> updateUserType(
          @RequestParam(value = "userId") Integer userId,
          @RequestParam(value = "userType") String userType
    ){
        AuthenticationResponse authenticationResponse = userService.updateUserType(userId, userType);
        return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);

    }

    @PutMapping("/updatePassword")
    public  ResponseEntity<ApiMessageResponse> updatePassword(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "currPassword") String currPassword,
            @RequestParam(value = "newPassword") String newPassword
            ){
        ApiMessageResponse response = userService.updatePassword(userId,currPassword,newPassword);
        if (response.getStatus()==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @DeleteMapping("/deleteUser{userId}")
    public ResponseEntity<ApiMessageResponse> deleteUser(@Valid @PathVariable("userId") Integer userId){
        userService.deleteUser(userId);
        ApiMessageResponse response = ApiMessageResponse.builder().success(true).message("User delete successfully !").status(HttpStatus.OK).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
