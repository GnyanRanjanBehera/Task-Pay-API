package com.task_pay.task_pay.models.entities;
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
    private String payStatus;

    @Column(nullable = false)
    private String payMethod;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double tax;

    @Column(nullable = false)
    private Date blockAt;

    @Column(nullable = false)
    private Date releasedAt;

    @OneToOne
    @JoinColumn(name = "taskId")
    private Task task;

}
