package com.task_pay.task_pay.utils.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String mobileNumber;
    private String password;
}
