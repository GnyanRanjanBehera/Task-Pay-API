package com.task_pay.task_pay.models.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
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


    private Date startDate;


    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne(mappedBy = "mileStone",cascade = CascadeType.ALL,orphanRemoval=true)
    private MileStonePayment mileStonePayment;

}
