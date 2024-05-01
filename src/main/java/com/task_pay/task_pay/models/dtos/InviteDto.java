package com.task_pay.task_pay.models.dtos;
import lombok.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteDto {
    private Integer inviteId;
    private Date invitedAt;
    private UserDto inviteUser;
    private UserDto invitedUser;
}
