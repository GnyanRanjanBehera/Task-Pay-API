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
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer inviteId;

    @Column(nullable = false)
    private Date invitedAt;

    @Column(nullable = false)
    private Integer inviteUserId;

    @ManyToOne
    @JoinColumn(name = "userId")
    public User user;

}
