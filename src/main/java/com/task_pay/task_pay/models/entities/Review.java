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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Integer reviewId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private Date timeStamp;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Reply> replies=new ArrayList<>();
}
