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
    private Integer milestonePaymentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus milestonePaymentStatus;

    @Column(nullable = false)
    private String milestonePaymentMethod;

    @Column(nullable = false)
    private double milestonePaymentAmount;

    @Column(nullable = false)
    private double milestonePaymentTax;

    @Column(nullable = false)
    private Date milestonePaymentBlockAt;

    private Date milestonePaymentReleasedRequestAt;

    private Date milestonePaymentReleasedAt;

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
