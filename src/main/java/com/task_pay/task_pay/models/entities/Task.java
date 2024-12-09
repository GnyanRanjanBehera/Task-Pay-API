package com.task_pay.task_pay.models.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.task_pay.task_pay.models.enums.TaskStatus;
import com.task_pay.task_pay.models.enums.UserType;
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
    private double taskPrice;

    @Column(nullable = false)
    private String taskAbout;

    @Column(nullable = false)
    private  String isFullPayment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType senderUserType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType receiverUserType;

    @Column(nullable = false)
    private Date createdAt;

    private Date blockedAt;

    private Date acceptedAt;

    private Date declinedAt;

    private Date releasedRequestAt;

    private Date releasedAt;

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
