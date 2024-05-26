package com.task_pay.task_pay.payloads.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String mobileNumber;
    private String password;
}
