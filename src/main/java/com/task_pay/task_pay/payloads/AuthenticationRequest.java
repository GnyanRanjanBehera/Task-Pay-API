package com.task_pay.task_pay.payloads;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String mobileNumber;
    private String password;
}
