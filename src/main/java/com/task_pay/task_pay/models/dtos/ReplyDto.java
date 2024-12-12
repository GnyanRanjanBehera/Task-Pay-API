package com.task_pay.task_pay.models.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReplyDto {

    private int replyId;

    @NotBlank(message = "required")
    private String content;

    private Date timeStamp;

    private UserDto user;
}
