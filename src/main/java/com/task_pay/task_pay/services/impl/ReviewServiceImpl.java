package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.ReplyDto;
import com.task_pay.task_pay.models.dtos.ReviewDto;
import com.task_pay.task_pay.models.entities.Reply;
import com.task_pay.task_pay.models.entities.Review;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.payloads.PageableResponse;
import com.task_pay.task_pay.payloads.ReplyRequest;
import com.task_pay.task_pay.repositories.ReplyRepository;
import com.task_pay.task_pay.repositories.ReviewRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.ReviewService;
import com.task_pay.task_pay.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReviewDto addReview(ReviewDto reviewDto, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        Review review = mapper.map(reviewDto, Review.class);
        review.setUser(user);
        review.setTimeStamp(new Date());
        Review saveReview = reviewRepository.save(review);
        return mapper.map(saveReview,ReviewDto.class);
    }

    @Override
    public ReplyDto addReply(ReplyDto replyDto, int userId, int reviewId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id !"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found with this id !"));
        replyDto.setTimeStamp(new Date());
        Reply reply = mapper.map(replyDto, Reply.class);
        reply.setUser(user);
        reply.setReview(review);
        Reply saveReply = replyRepository.save(reply);
        return mapper.map(saveReply, ReplyDto.class);
    }

    @Override
    public void deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found with this id !"));
        reviewRepository.delete(review);
    }

    @Override
    public void deleteReply(ReplyRequest request) {
        int reviewId=request.getReviewId();
        int replyId=request.getReplyId();
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("review not found with this id !"));
        List<Reply> replies = review.getReplies();
        replies.removeIf(reply -> reply.getReplyId()==replyId);
        reviewRepository.save(review);
    }

    @Override
    public PageableResponse<ReviewDto> fetchReviews(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return Helper.getPageableResponse(reviews,ReviewDto.class);
    }
}
