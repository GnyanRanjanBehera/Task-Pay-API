package com.task_pay.task_pay.services;

import com.task_pay.task_pay.payloads.NotificationRequest;

import java.util.concurrent.ExecutionException;

public interface FCMService {
    void sendMessageToToken(NotificationRequest request) throws InterruptedException, ExecutionException;
}
