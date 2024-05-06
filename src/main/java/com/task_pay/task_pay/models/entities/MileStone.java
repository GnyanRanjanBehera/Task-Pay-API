package com.task_pay.task_pay.models.entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mileStoneId;
    private String mileStoneName;
    private String mileStonePrice;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne(mappedBy = "mileStone",cascade = CascadeType.ALL,orphanRemoval=true)
    private MileStonePayment mileStonePayment;

}
