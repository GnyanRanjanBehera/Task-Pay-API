package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.entities.Invite;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.repositories.InviteRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.InviteService;
import com.task_pay.task_pay.utils.Helper;
import com.task_pay.task_pay.utils.request.InviteUserRequest;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public InviteDto inviteUser(InviteUserRequest inviteUserRequest) {
        String mobileNumber = inviteUserRequest.getMobileNumber();
        String invitationCode=inviteUserRequest.getInvitationCode();
        Integer userId = inviteUserRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this userId"));
        if(mobileNumber==null && invitationCode!=null && user!=null){
            User inviteUser = userRepository
                    .findByInvitationCode(invitationCode).orElseThrow(() -> new ResourceNotFoundException("User not found with this invitation code !"));
            Optional<Invite> invitedUser = inviteRepository.findById(inviteUser.getUserId());
            if(invitedUser.isEmpty()){
                Invite invite=new Invite();
                invite.setInvitedAt(new Date());
                invite.setInviteUserId(userId);
                invite.setUser(inviteUser);
                inviteRepository.save(invite);
                return mapper.map(invite,InviteDto.class);
            }else{
                return mapper.map(invitedUser.get(),InviteDto.class);
            }
        } else if(invitationCode==null && mobileNumber!=null&& user!=null){
            User inviteUser = userRepository
                    .findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("User not found with this mobile number !"));
            Optional<Invite> invitedUser = inviteRepository.findById(inviteUser.getUserId());
            if(invitedUser.isEmpty()){
                Invite invite=new Invite();
                invite.setInvitedAt(new Date());
                invite.setInviteUserId(userId);
                invite.setUser(inviteUser);
                inviteRepository.save(invite);
                return mapper.map(invite,InviteDto.class);
            }else{

                return mapper.map(invitedUser.get(),InviteDto.class);
            }

        }else{
            User inviteUser = userRepository.findByMobileNumberAndInvitationCode(mobileNumber,invitationCode)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with this mobile number & invitation code !"));
            Optional<Invite> invitedUser = inviteRepository.findById(inviteUser.getUserId());
            if(invitedUser.isEmpty()){
                Invite invite=new Invite();
                invite.setInvitedAt(new Date());
                invite.setInviteUserId(userId);
                invite.setUser(inviteUser);
                inviteRepository.save(invite);
                return mapper.map(invite,InviteDto.class);
            }else{

                return mapper.map(invitedUser.get(),InviteDto.class);
            }

        }
    }

    @Override
    public PageableResponse<InviteDto> fetchInvitedUsers(Integer userId,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Objects[]> invitedUsersByUserId = inviteRepository.findInvitedUsersByUserId(userId, pageable);
        return Helper.getPageableResponse(invitedUsersByUserId, InviteDto.class);
    }

    @Override
    public void deleteInvitedUser(String userId, String inviteId) {

    }
}
