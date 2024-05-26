package com.task_pay.task_pay.payloads.request;

import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskFileDto;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AssignTaskRequest {

    private Integer senderUserId;

    private Integer receiverUserId;

    private String taskName;

    private Integer taskPrice;

    private String taskAbout;

    @Null
    private List<MileStoneDto> mileStones;

    @Null
    private List<MultipartFile> files;
}
