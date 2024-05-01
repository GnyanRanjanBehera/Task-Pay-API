package com.task_pay.task_pay.services;


import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TaskService {

    TaskDto assignTask(Integer userId, Integer assignUserId,
                       String taskName, Integer taskPrice,
                       String taskAbout, List<MileStoneDto> mileStoneDtos);

    PageableResponse<TaskDto> fetchBuyerTasks(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);
    PageableResponse<TaskDto> fetchSellerTasks(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);

}
