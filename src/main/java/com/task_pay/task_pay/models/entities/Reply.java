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
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer replyId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date timeStamp;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
