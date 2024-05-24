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
    private Integer paymentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private double paymentAmount;

    @Column(nullable = false)
    private double paymentTax;

    @Column(nullable = false)
    private Date paymentBlockAt;

    private Date paymentReleasedRequestAt;

    private Date paymentReleasedAt;

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
