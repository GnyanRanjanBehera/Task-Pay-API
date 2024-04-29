package com.task_pay.task_pay.controllers;


import com.task_pay.task_pay.models.dtos.InviteDto;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.services.InviteService;
import com.task_pay.task_pay.utils.request.InviteUserRequest;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
import com.task_pay.task_pay.utils.response.PageableResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/invite")
public class InviteController {

    @Autowired
    private InviteService inviteService;

    @PostMapping("/inviteUser")
    public ResponseEntity<InviteDto> inviteUser(
           @Valid @RequestBody InviteUserRequest inviteUserRequest
            ) {
        return new ResponseEntity<>(inviteService.inviteUser(inviteUserRequest), HttpStatus.OK);
    }

    @GetMapping("/fetchInvitedUsers/{userId}")
    public ResponseEntity<PageableResponse<InviteDto>> fetchInvitedUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "invitedAt",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @PathVariable("userId") Integer userId
    ){
        PageableResponse<InviteDto> inviteDtoPageableResponse = inviteService.fetchInvitedUsers(userId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(inviteDtoPageableResponse,HttpStatus.OK);
    }
}
