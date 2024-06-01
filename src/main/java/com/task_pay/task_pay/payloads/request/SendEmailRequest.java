package com.task_pay.task_pay.payloads.request;
import lombok.Data;

@Data
public class SendEmailRequest {
    private String to;
    private String subject;
    private String body;
}
