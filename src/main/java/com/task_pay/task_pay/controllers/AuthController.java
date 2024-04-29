package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.services.AuthService;

import com.task_pay.task_pay.utils.request.AuthenticationRequest;
import com.task_pay.task_pay.utils.response.ApiMessageResponse;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
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

    @PostMapping("/sendOTP")
    public  ResponseEntity<ApiMessageResponse> sendOTP(@Valid @RequestBody UserDto userDto){
        ApiMessageResponse response = authService.sendOTP(userDto);
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
    public ResponseEntity<AuthenticationResponse> signIn(
            @Valid  @RequestBody AuthenticationRequest request
    ) {
        return new ResponseEntity<>(authService.signIn(request), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
