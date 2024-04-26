package com.task_pay.task_pay.models.dtos;
import com.task_pay.task_pay.models.entities.User;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteDto {
    private Integer inviteId;

    public User user;

}
