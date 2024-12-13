package com.task_pay.task_pay.models.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer wId;

    private double wAmount;

    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;


}
