package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.UserDto;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this userId"));
        if(mobileNumber==null && invitationCode!=null && user!=null){
            User invitedUser = userRepository.findByInvitationCode(invitationCode).orElseThrow(() -> new ResourceNotFoundException("Invitation code does not exist !"));
            Optional<Invite> checkInvitedUser = inviteRepository.findByInvitedUserId(invitedUser.getUserId());
            if(checkInvitedUser.isEmpty()){
                InviteDto inviteDto = InviteDto.builder()
                        .inviteUser(mapper.map(user, UserDto.class))
                        .invitedUser(mapper.map(invitedUser, UserDto.class))
                        .invitedAt(new Date())
                        .build();
                Invite saveInvite = inviteRepository.save(mapper.map(inviteDto, Invite.class));
                return mapper.map(saveInvite,InviteDto.class);
            }else{
                return mapper.map(checkInvitedUser.get(),InviteDto.class);
            }
        } else if(invitationCode==null && mobileNumber!=null&& user!=null){
            User invitedUser = userRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Mobile number does not exist !"));
            Optional<Invite> checkInvitedUser = inviteRepository.findByInvitedUserId(invitedUser.getUserId());
            if(checkInvitedUser.isEmpty()){
                InviteDto inviteDto = InviteDto.builder()
                        .inviteUser(mapper.map(user, UserDto.class))
                        .invitedUser(mapper.map(invitedUser, UserDto.class))
                        .invitedAt(new Date())
                        .build();
                Invite saveInvite = inviteRepository.save(mapper.map(inviteDto, Invite.class));
                return mapper.map(saveInvite,InviteDto.class);
            }else{
                return mapper.map(checkInvitedUser.get(),InviteDto.class);
            }

        }else{
            User invitedUser = userRepository.findByMobileNumberAndInvitationCode(mobileNumber,invitationCode)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with this mobile number & invitation code !"));
            Optional<Invite> checkInvitedUser = inviteRepository.findByInvitedUserId(invitedUser.getUserId());
            if(checkInvitedUser.isEmpty()){
                InviteDto inviteDto = InviteDto.builder()
                        .inviteUser(mapper.map(user, UserDto.class))
                        .invitedUser(mapper.map(invitedUser, UserDto.class))
                        .invitedAt(new Date())
                        .build();
                Invite saveInvite = inviteRepository.save(mapper.map(inviteDto, Invite.class));
                return mapper.map(saveInvite,InviteDto.class);
            }else{
                return mapper.map(checkInvitedUser.get(),InviteDto.class);
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
    public void removeInvitedUser(Integer userId, Integer inviteId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this userId !"));
        Invite invite = inviteRepository.findById(inviteId).orElseThrow(() -> new ResourceNotFoundException("User not found with this invitedUserId !"));
        if(user!=null && invite!=null){
            inviteRepository.delete(invite);
        }

    }
}
