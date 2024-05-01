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
    @JoinColumn(name = "inviteUserId",referencedColumnName = "userId")
    private User inviteUser;

    @ManyToOne
    @JoinColumn(name = "invitedUserId",referencedColumnName = "userId")
    public User invitedUser;

}
