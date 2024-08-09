package com.task_pay.task_pay.payloads;

import com.task_pay.task_pay.models.dtos.UserDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    private UserDto user;
}
