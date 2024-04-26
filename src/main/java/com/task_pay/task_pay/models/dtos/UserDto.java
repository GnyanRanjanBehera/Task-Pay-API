package com.task_pay.task_pay.models.dtos;
import jakarta.persistence.Column;
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

    @NotBlank(message = "mobileNumber is blank")
    private String mobileNumber;

    @NotBlank(message = "email is blank")
    private String email;

    @NotBlank(message = "password is blank")
    private String password;

    private String expertIn;

    private String about;

    private String invitationCode;

    private Date optVerifiedAt;

    private Date updatedAt;

    private Date createdAt;

    private boolean isBlock;
}
