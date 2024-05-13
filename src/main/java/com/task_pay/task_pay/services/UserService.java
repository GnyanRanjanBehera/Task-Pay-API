package com.task_pay.task_pay.services;

import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.utils.response.ApiMessageResponse;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
import com.task_pay.task_pay.utils.response.PageableResponse;

public interface UserService {

    AuthenticationResponse updateProfile(UserDto userDto);
    AuthenticationResponse updateUserType(Integer userId, String userType);
    AuthenticationResponse updateMobileNumber(Integer userId,String mobileNumber);
    ApiMessageResponse updatePassword(Integer userId, String currPassword, String newPassword);
    UserDto fetchUserById(Integer userId);
    PageableResponse<UserDto> searchUser(String keywords,int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<UserDto> fetchUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteUser(Integer userId);
}
