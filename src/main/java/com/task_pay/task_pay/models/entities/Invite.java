package com.task_pay.task_pay.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer inviteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @ToString.Exclude
    public User user;

}
