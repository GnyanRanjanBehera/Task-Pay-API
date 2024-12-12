package com.task_pay.task_pay.services;
import com.task_pay.task_pay.models.dtos.MileStoneDto;
import com.task_pay.task_pay.models.dtos.TaskDto;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.payloads.UpdateTaskReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface TaskService {

    TaskDto assignTask(Integer senderUserId, Integer receiverUserId,String isFullPayment,
                       String taskName, double taskPrice,
                       String taskAbout, List<MultipartFile> taskFiles,List<MileStoneDto> mileStoneDtos) throws IOException, ParseException;

    TaskDto updateTask(UpdateTaskReq updateTaskReq);

    void deleteTask(Integer userId,Integer taskId);


    List<MileStoneDto> getJson(String milestones) throws IOException;


    TaskDto fetchTaskById(Integer taskId);

    PageableResponse<TaskDto> fetchBuyerTasks(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);
    PageableResponse<TaskDto> fetchSellerTasks(Integer userId,int pageNumber,int pageSize,String sortBy, String sortDir);

    TaskDto acceptTask(Integer userId,Integer taskId);

    TaskDto declineTask(Integer userId,Integer taskId);

}
