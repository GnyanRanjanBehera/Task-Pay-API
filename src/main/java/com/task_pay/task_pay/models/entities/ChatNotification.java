package com.task_pay.task_pay.models.entities;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {

    private Integer id;
    private Integer senderId;
    private Integer recipientId;
    private String content;
}
