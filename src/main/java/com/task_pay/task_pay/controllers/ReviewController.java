package com.task_pay.task_pay.controllers;

import com.task_pay.task_pay.models.dtos.ReplyDto;
import com.task_pay.task_pay.models.dtos.ReviewDto;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.payloads.ReplyRequest;
import com.task_pay.task_pay.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/addReview/{userId}")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable int userId){
        ReviewDto review = reviewService.addReview(reviewDto,userId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping("/addReply/{userId}/{reviewId}")
    public ResponseEntity<ReplyDto> addReply(@Valid @RequestBody ReplyDto replyDto, @PathVariable int userId, @PathVariable int reviewId){
        ReplyDto reply = reviewService.addReply(replyDto,userId,reviewId);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }
    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<ApiMessageResponse> deleteReview(@PathVariable int reviewId){
        reviewService.deleteReview(reviewId);
        ApiMessageResponse response = ApiMessageResponse.builder().message("Review deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/deleteReply")
    public ResponseEntity<ApiMessageResponse> deleteReply(@Valid @RequestBody ReplyRequest request){
        reviewService.deleteReply(request);
        ApiMessageResponse response = ApiMessageResponse.builder().message("Reply deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/fetchReviews")
    public  ResponseEntity<PageableResponse<ReviewDto>> fetchReviews(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "timeStamp",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return  new ResponseEntity<>(reviewService.fetchReviews(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

}
