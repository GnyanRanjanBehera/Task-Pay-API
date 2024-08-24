package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.AuthService;

import com.task_pay.task_pay.services.UserService;
import com.task_pay.task_pay.payloads.AuthenticationRequest;
import com.task_pay.task_pay.payloads.SendOtpRequest;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.io.IOException;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sendOTP")
    public  ResponseEntity<ApiMessageResponse> sendOTP(@Valid @RequestBody SendOtpRequest request) throws MessagingException, IOException {
        ApiMessageResponse response = authService.sendOTP(request);
        if (response.getStatus()==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/verifyOTP/{OTP}")
    public ResponseEntity<AuthenticationResponse> verifyOTP(
            @Valid @RequestBody UserDto userDto,@PathVariable("OTP") String OTP
    ) {
        return new ResponseEntity<>(authService.verifyOTP(userDto,OTP), HttpStatus.OK);
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(
            @Valid  @RequestBody AuthenticationRequest request
    ) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("user not found with this email and password !"));
        if(user.isBlock()){
            ApiMessageResponse response = ApiMessageResponse.
                    builder().message("Something went wrong !").success(false).status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(authService.signIn(request), HttpStatus.OK);
        }

    }

    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
