package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public InviteDto inviteUser(String mobileNumber, String invitationCode) {
        return null;
    }
}
