package com.task_pay.task_pay.models.entities;


import com.task_pay.task_pay.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MileStonePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mPayId;

    @Column(nullable = false)
    private String orderId;

    private String paymentId;

    private Date createdAt;

    @Column(nullable = false)
    private String receipt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String method;

    @Column(nullable = false)
    private double amount;


    private Date blockedAt;

    private  Date processingAt;

    private Date releasedRequestAt;

    private Date releasedAt;

    @ManyToOne
    @JoinColumn(name = "senderUserId")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiverUserId")
    private User receiverUser;

    @OneToOne
    @JoinColumn(name = "mileStoneId")
    private MileStone mileStone;


}
