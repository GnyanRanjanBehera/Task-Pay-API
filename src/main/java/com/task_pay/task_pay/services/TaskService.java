package com.task_pay.task_pay.services;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.models.dtos.TaskFileDto;
import com.task_pay.task_pay.utils.request.AssignTaskRequest;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface TaskService {

    TaskDto assignTask(Integer userId, Integer assignUserId,
                       String taskName, Integer taskPrice,
                       String taskAbout, List<MultipartFile> taskFiles,List<MileStoneDto> mileStoneDtos) throws IOException;

    List<MileStoneDto> getJson(String milestones) throws IOException;

    PageableResponse<TaskDto> fetchBuyerTasks(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);
    PageableResponse<TaskDto> fetchSellerTasks(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);

    TaskDto acceptTask(Integer userId,Integer taskId);

}
