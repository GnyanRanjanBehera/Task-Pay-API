package com.task_pay.task_pay.payloads;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteUserRequest {
    @NotNull(message = "userId required")
    private Integer userId;
    private String mobileNumber;
    private String invitationCode;
}
