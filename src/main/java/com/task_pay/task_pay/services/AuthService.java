package com.task_pay.task_pay.services;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.utils.request.AuthenticationRequest;
import com.task_pay.task_pay.utils.request.SendOtpRequest;
import com.task_pay.task_pay.utils.response.ApiMessageResponse;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

public interface AuthService {

    ApiMessageResponse sendOTP(SendOtpRequest request);
    AuthenticationResponse verifyOTP(UserDto userDto,String OTP);

    AuthenticationResponse signIn(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void blockUser(Integer userId);
    void unBlockUser(Integer userId);
}
