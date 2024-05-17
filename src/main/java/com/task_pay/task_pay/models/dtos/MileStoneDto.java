package com.task_pay.task_pay.models.dtos;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class MileStoneDto {
    private Integer id;
    private String mileStoneName;
    private Integer mileStonePrice;
}
