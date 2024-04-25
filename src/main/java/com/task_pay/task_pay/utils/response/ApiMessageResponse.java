package com.task_pay.task_pay.utils.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiMessageResponse {
    private  String message;
    private  boolean success;
    private HttpStatus status;
}
