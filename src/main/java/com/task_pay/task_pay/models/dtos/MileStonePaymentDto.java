package com.task_pay.task_pay.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.task_pay.task_pay.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MileStonePaymentDto {


    private Integer mPayId;

    private String orderId;

    private String paymentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date createdAt;

    private String receipt;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String method;


    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date successAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private  Date processingAt;


    private UserDto senderUser;

    private UserDto receiverUser;

}
