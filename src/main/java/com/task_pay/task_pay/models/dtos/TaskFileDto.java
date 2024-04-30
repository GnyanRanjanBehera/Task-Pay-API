package com.task_pay.task_pay.models.dtos;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskFileDto {
    private Integer fileId;
    private String url;
}
