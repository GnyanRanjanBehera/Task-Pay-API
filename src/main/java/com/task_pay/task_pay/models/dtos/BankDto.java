package com.task_pay.task_pay.models.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BankDto {
    private Integer bankId;

    @NotBlank(message = "Bank name is blank")
    private  String bankName;



    @NotBlank(message = "Bank account number is blank")
    private String accNumber;

    @NotBlank(message = "Bank IFSC is blank")
    private String IFSC;

    @NotBlank(message = "Bank account holder is blank")
    private String accHolderName;

    @NotBlank(message = "Mobile number is blank")
    private String mobileNUmber;

    private UserDto user;

}
