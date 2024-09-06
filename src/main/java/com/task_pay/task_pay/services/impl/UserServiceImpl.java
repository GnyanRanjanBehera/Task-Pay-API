package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.models.enums.UserType;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.UserService;
import com.task_pay.task_pay.utils.Helper;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.AuthenticationResponse;
import com.task_pay.task_pay.payloads.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void addFcmToken(String email,String fcmToken) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found !"));
        user.setFcmToken(fcmToken);
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse updateProfile(UserDto userDto) {
        User user = userRepository.findById(userDto.getUserId()).
                orElseThrow(() -> new ResourceNotFoundException("User not found with given userId !"));
        if(StringUtils.hasText(userDto.getName())){
            user.setName(userDto.getName());
        }
        if(StringUtils.hasText(userDto.getExpertIn())){
            user.setExpertIn(userDto.getExpertIn());
        }
        if(StringUtils.hasText(userDto.getAbout())){
            user.setAbout(userDto.getAbout());
        }
        if(StringUtils.hasText(userDto.getMobileNumber())){
            user.setMobileNumber(userDto.getMobileNumber());
        }
        if(StringUtils.hasText(userDto.getEmail())){
            user.setMobileNumber(userDto.getEmail());
        }

        User saveUser = userRepository.save(user);
        return AuthenticationResponse.builder().user(mapper.map(saveUser,UserDto.class)).build();
    }

    @Override
    public AuthenticationResponse updateUserType(Integer userId, UserType userType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this userId !"));
        user.setUserType(userType);
        User saveUser = userRepository.save(user);
        return AuthenticationResponse.builder()
                .user(mapper.map(saveUser,UserDto.class))
                .build();
    }


    @Override
    public ApiMessageResponse updatePassword(Integer userId, String currPassword, String newPassword) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with this userId !"));
        boolean isMatchPass = passwordEncoder.matches(currPassword, user.getPassword());
        if(isMatchPass){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ApiMessageResponse.builder()
                    .message("Password change successfully !")
                    .status(HttpStatus.OK).success(true).build();
        }else{
            return ApiMessageResponse.builder()
                    .message("Your current password is wrong !")
                    .status(HttpStatus.NOT_FOUND).success(false).build();
        }

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
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with this userId !"));
        if(user!=null){
            userRepository.delete(user);
        }
    }
}
