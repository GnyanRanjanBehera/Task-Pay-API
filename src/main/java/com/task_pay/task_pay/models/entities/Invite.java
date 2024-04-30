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

    @ManyToOne
    @JoinColumn(name = "inviteUserId")
    private User inviteUser;

    @ManyToOne
    @JoinColumn(name = "invitedUserId")
    public User invitedUser;

}
