package com.task_pay.task_pay.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
    private  String imageName;
    private  String message;
    private  boolean success;
    private HttpStatus status;
}
