package com.task_pay.task_pay.payloads;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskReq {
    private Integer userId;
    private Integer taskId;
    private String taskName;
    private  String taskAbout;
    private  double taskPrice;

}
