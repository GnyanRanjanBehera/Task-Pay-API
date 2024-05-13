package com.task_pay.task_pay.models.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {

    @Id
    private Integer id;
    private Integer chatId;
    private Integer senderId;
    private Integer recipientId;
    private String content;
    private Date timestamp;
}
