package com.task_pay.task_pay.services;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    void sendEmailWithOTP(String deviceId, String email) throws MessagingException;
}
