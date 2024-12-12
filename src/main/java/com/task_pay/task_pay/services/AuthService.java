package com.task_pay.task_pay.services;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.payloads.AuthenticationRequest;
import com.task_pay.task_pay.payloads.SendOtpRequest;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    ApiMessageResponse sendOTP(SendOtpRequest request) throws MessagingException;
    AuthenticationResponse verifyOTP(UserDto userDto,String OTP);
    AuthenticationResponse signIn(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void blockUser(Integer userId);
    void unBlockUser(Integer userId);
}
