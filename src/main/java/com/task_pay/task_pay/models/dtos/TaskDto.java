package com.task_pay.task_pay.models.dtos;
import com.task_pay.task_pay.models.entities.TaskFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Integer taskId;

    @NotBlank(message = "required taskName !")
    private String taskName;

    @NotNull(message = "required assignUserId !")
    private Integer assignUserId;


    @NotNull(message = "required taskPrice !")
    private Integer taskPrice;

    @NotBlank(message = "required taskAbout !")
    private String taskAbout;

//    private List<String> taskFiles;

    private List<MileStoneDto> mileStones;
}
