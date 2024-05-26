package com.task_pay.task_pay.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpRequest {

    @NotBlank(message = "mobileNumber required")
    private String mobileNumber;

    @NotBlank(message = "mobileNumber required")
    private String email;
}

