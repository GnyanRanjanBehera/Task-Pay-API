package com.task_pay.task_pay.services;

public interface EmailService {

    void sendEmailWithAttachment(String deviceId, String email, String devOrProduction);
}
