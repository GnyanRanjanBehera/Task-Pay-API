package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.services.AuthService;
import com.task_pay.task_pay.services.UserService;
import com.task_pay.task_pay.utils.request.AuthenticationRequest;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto userDto1=authService.signUp(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return new ResponseEntity<>(authService.signIn(request), HttpStatus.CREATED);
    }

    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
