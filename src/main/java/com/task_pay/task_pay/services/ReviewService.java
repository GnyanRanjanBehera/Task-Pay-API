package com.task_pay.task_pay.services;
import com.task_pay.task_pay.models.dtos.ReplyDto;
import com.task_pay.task_pay.models.dtos.ReviewDto;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.payloads.ReplyRequest;

public interface ReviewService {

    ReviewDto addReview(ReviewDto reviewDto, int userId);

    ReplyDto addReply(ReplyDto replyDto, int userId, int reviewId);

    void deleteReview(int reviewId);

    void deleteReply(ReplyRequest request);

    PageableResponse<ReviewDto> fetchReviews(int pageNumber, int pageSize, String sortBy, String sortDir);

}
