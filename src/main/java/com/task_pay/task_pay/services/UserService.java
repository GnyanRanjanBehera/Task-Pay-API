package com.task_pay.task_pay.services;

import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.utils.response.PageableResponse;

public interface UserService {

    UserDto updateUser(UserDto userDto);
    UserDto fetchUserById(Integer userId);
    PageableResponse<UserDto> searchUser(String keywords,int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<UserDto> fetchUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    UserDto blockUser(Integer userId);
    void deleteUser(Integer userId);
}
