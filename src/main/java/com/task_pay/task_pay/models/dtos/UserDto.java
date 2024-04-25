package com.task_pay.task_pay.models.dtos;
import lombok.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int userId;

    private String userType;

    private String name;

    private String mobileNumber;

    private String email;

    private String password;

    private String expertIn;

    private String about;

    private String profilePic;

    private Date createAt;
}
