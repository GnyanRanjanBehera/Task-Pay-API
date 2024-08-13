package com.task_pay.task_pay.models.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer bankId;

    @Column(nullable = false)
    private  String bankName;

    @Column(nullable = false)
    private String accNumber;

    @Column(nullable = false)
    private String IFSC;

    @Column(nullable = false)
    private String accHolderName;

    @Column(nullable = false)
    private String mobileNUmber;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
