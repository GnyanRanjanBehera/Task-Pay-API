package com.task_pay.task_pay.models.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MileStoneDto {
//    private Integer id;
    private String mileStoneName;
    private Integer mileStonePrice;
}
