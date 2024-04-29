package com.task_pay.task_pay.services;

import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.utils.request.InviteUserRequest;
import com.task_pay.task_pay.utils.response.PageableResponse;

public interface InviteService {

    InviteDto inviteUser(InviteUserRequest inviteUserRequest);

    PageableResponse<InviteDto> fetchInvitedUsers(Integer userId,int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteInvitedUser(String userId,String inviteId);

}
