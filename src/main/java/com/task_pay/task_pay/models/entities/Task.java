package com.task_pay.task_pay.models.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
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
    private String taskName;

    @Column(nullable = false)
    private Integer taskPrice;

    @Column(nullable = false)
    private String taskAbout;

    @Column(nullable = false)
    private String taskStatus;

    @Column(nullable = false)
    private String senderUserType;

    @Column(nullable = false)
    private String receiverUserType;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "senderUserId")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiverUserId")
    private User receiverUser;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<TaskFile> taskFiles=new ArrayList<>();

    @OneToOne(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval=true)
    private Payment payment;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<MileStone> mileStones=new ArrayList<>();

}
