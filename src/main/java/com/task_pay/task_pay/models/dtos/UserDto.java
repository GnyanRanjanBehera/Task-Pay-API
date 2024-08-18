package com.task_pay.task_pay.models.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task_pay.task_pay.models.enums.Role;
import com.task_pay.task_pay.models.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NotBlank(message = "name is blank")
    private String name;
    
    private String profilePic;

    @NotBlank(message = "mobileNumber is blank")
    private String mobileNumber;

    @NotBlank(message = "email is blank")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role=Role.USER;

    @NotBlank(message = "password is blank")
    private String password;

    private String fcmToken;

    private String expertIn;

    private String about;

    private String invitationCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date otpVerifiedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date createdAt;

    private boolean isBlock;
}
