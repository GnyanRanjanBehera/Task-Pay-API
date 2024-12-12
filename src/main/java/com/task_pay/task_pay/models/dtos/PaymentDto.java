package com.task_pay.task_pay.models.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.task_pay.task_pay.models.entities.Task;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.models.enums.PaymentStatus;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentDto {

    private Integer payId;

    private String orderId;

    private String paymentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date createdAt;

    private String receipt;

    private PaymentStatus status;

    private String method;

    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date successAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private  Date processingAt;

    private Integer senderUserId;

    private Integer receiverUserId;

    private Integer taskId;

}
