package com.task_pay.task_pay.payloads;


import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMilestoneReq {
    private Integer userId;
    private Integer milestoneId;
    private String milestoneName;
    private  double milestonePrice;
    private Date startDate;
    private Date endDate;
}
