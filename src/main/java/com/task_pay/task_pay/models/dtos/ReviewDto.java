package com.task_pay.task_pay.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewDto {
    private  int reviewId;

    @NotBlank(message = "required")
    private String content;

    @NotNull(message = "required")
    private double rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date timeStamp;

    private UserDto user;

    List<ReplyDto> replies=new ArrayList<>();;
}