package com.task_pay.task_pay.services;

import com.task_pay.task_pay.models.dtos.InviteDto;

public interface InviteService {

    InviteDto inviteUser(String mobileNumber,String invitationCode);

}
