package com.task_pay.task_pay.services;

import com.task_pay.task_pay.payloads.request.SendEmailRequest;

public interface EmailService {

    void sendEmailWithAttachment(String deviceId, String email, String devOrProduction);
}
