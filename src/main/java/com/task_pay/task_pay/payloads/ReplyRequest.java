package com.task_pay.task_pay.payloads;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReplyRequest {
    @NotNull(message = "required")
    private int reviewId;

    @NotNull(message = "required")
    private int replyId;
}