package com.task_pay.task_pay.payloads;
import lombok.Data;

@Data
public class SendEmailRequest {
    private String to;
    private String subject;
    private String body;
}
