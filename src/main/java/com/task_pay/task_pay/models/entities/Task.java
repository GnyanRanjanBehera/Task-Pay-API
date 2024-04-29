package com.task_pay.task_pay.models.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer taskId;

    @Column(nullable = false)
    private Integer assignUserId;

    @Column(nullable = false)
    private String taskName;

    @Column(nullable = false)
    private Integer taskPrice;

    @Column(nullable = false)
    private String taskAbout;

//
//    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval=true)
//    private List<TaskFile> taskFiles=new ArrayList<>();

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<MileStone> mileStones=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
