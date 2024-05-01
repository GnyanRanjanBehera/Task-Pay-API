package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.UserService;
import com.task_pay.task_pay.utils.Helper;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto fetchUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this userId !"));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> searchUser(String keywords,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Objects[]> user = userRepository.findByKeyword(keywords,pageable);
        return Helper.getPageableResponse(user, UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> fetchUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> users = userRepository.findAll(pageable);
        return Helper.getPageableResponse(users, UserDto.class);
    }

    @Override
    public UserDto blockUser(Integer userId) {
        return null;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this userId !"));
        if(user!=null){
            userRepository.delete(user);
        }

    }
}
