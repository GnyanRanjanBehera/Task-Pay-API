package com.task_pay.task_pay.models.dtos;
import lombok.*;
import java.util.Date;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private Integer wId;
    private double wAmount;
    private Date updatedAt;
}
