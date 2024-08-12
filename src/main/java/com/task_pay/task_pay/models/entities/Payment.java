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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer payId;

    @Column(nullable = false)
    private String orderId;

    private String paymentId;

    private Date createdAt;

    private String receipt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String method;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date blockedAt;

    private Date releasedRequestAt;

    private Date releasedAt;

    @ManyToOne
    @JoinColumn(name = "senderUserId")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiverUserId")
    private User receiverUser;

    @OneToOne
    @JoinColumn(name = "taskId")
    private Task task;

}
