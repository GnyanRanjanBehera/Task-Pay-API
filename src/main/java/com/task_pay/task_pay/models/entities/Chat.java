package com.task_pay.task_pay.models.entities;

import jakarta.persistence.Id;

import java.util.Date;

public class Chat {

    @Id
    private Integer id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Date timestamp;

}
