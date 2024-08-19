package com.task_pay.task_pay.models.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteDto {
    private Integer inviteId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date invitedAt;
    private UserDto inviteUser;
    private UserDto invitedUser;
}
