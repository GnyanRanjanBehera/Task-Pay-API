package com.task_pay.task_pay.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer fileId;

    private String url;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

}
