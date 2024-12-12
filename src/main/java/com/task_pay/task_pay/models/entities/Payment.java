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

    @Column(nullable = false)
    private String receipt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String method;

    @Column(nullable = false)
    private double amount;


    private Date successAt;

    private  Date processingAt;

    @ManyToOne
    @JoinColumn(name = "senderUserId")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiverUserId")
    private User receiverUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId")
    private Task task;

}
