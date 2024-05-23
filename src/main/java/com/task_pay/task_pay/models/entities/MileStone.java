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
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mileStoneId;
    private String mileStoneName;
    private double mileStonePrice;
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne(mappedBy = "mileStone",cascade = CascadeType.ALL,orphanRemoval=true)
    private MileStonePayment mileStonePayment;

}
