package com.task_pay.task_pay.models.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task_pay.task_pay.models.enums.TaskStatus;
import com.task_pay.task_pay.models.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TaskDto {
    private Integer taskId;

    @NotBlank(message = "required taskName !")
    private String taskName;

    @NotNull(message = "required taskPrice !")
    private double taskPrice;

    @NotBlank(message = "required taskAbout !")
    private String taskAbout;

    @NotBlank(message = "required isFullPayment !")
    private String isFullPayment;


    @Enumerated(EnumType.STRING)
    private UserType senderUserType;

    @Enumerated(EnumType.STRING)
    private UserType receiverUserType;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date acceptedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private  Date declinedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date releasedRequestAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private  Date releasedAt;

    private UserDto senderUser;

    private UserDto receiverUser;

    private List<TaskFileDto> taskFiles;

    private List<MileStoneDto> mileStones;
}
