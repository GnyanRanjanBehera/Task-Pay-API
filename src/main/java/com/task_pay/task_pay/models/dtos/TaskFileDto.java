package com.task_pay.task_pay.models.dtos;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskFileDto {
    private Integer fileId;
    private MultipartFile url;
}
