package com.task_pay.task_pay.payloads;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
    private Integer taskId;
    private Integer senderUserId;
    private Integer receiverUserId;
}
