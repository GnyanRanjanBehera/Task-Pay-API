package com.task_pay.task_pay.models.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.task_pay.task_pay.models.enums.MilestoneStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MilestoneStatus milestoneStatus;

    @Column(nullable = false)
    private Date createdAt;

    private Date blockedAt;

    private Date updatedAt;

    private  Date releasedRequestAt;

    private Date releasedAt;

    private Date startDate;

    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne(mappedBy = "mileStone",cascade = CascadeType.ALL,orphanRemoval=true)
    private MileStonePayment mileStonePayment;

}
