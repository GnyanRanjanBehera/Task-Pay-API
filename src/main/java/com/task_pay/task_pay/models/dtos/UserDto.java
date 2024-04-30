package com.task_pay.task_pay.models.dtos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "name is blank")
    private String name;
    
    private String profilePic;

    @NotBlank(message = "mobileNumber is blank")
    private String mobileNumber;

    @NotBlank(message = "email is blank")
    private String email;

    @JsonIgnore
    @NotBlank(message = "password is blank")
    private String password;

    private String expertIn;

    private String about;

    private String invitationCode;

    private Date otpVerifiedAt;

    private Date updatedAt;

    private Date createdAt;

    private boolean isBlock;
}
