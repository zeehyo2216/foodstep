package com.example.foodstep.service;

import com.example.foodstep.domain.*;
import com.example.foodstep.dto.review.CommentResponseDto;
import com.example.foodstep.dto.review.ReviewRequestDto;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.CommentRepository;
import com.example.foodstep.repository.PlaceRepository;
import com.example.foodstep.repository.ReviewRepository;
import com.example.foodstep.repository.ReviewViewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.example.foodstep.enums.ErrorCode.PLACE_NOT_FOUND;
import static com.example.foodstep.enums.ErrorCode.REVIEW_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final ReviewViewedRepository reviewViewedRepository;
    private CommentRepository commentRepository;

    private static final Integer VIEW_TYPE_LIGHT = 1;
    private static final Integer VIEW_TYPE_DEEP = 2;

    // deprecate
    @Transactional
    public ReviewResponseDto findReviewDetail(int id){
        Review review = reviewRepository.searchById(id).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return reviewResponseDto;
    }

    @Transactional
    public List<CommentResponseDto> getComment(int reviewId, User user) {
        List<Comment> commentList = commentRepository.searchByReviewId(reviewId);
        List<CommentResponseDto> responseList =  commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        return responseList;
    }


    @Transactional
    public void addReview(ReviewRequestDto reviewRequestDto, User user) {
        Place place = placeRepository.findById(reviewRequestDto.getPlaceId()).orElseThrow(() -> new CustomException(PLACE_NOT_FOUND));

        Review review = Review.builder()
                        .user(user)
                        .place(place)
                        .rate(reviewRequestDto.getRate())
                        .keyword(reviewRequestDto.getKeyword())
                        .contents(reviewRequestDto.getContents())
                        .build();
        reviewRepository.save(review);
    }

    @Transactional
    public void editReview(ReviewRequestDto reviewRequestDto){
        Review review = reviewRepository.searchById(reviewRequestDto.getReviewId()).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        review.updateReview(reviewRequestDto.getRate(), reviewRequestDto.getKeyword(), reviewRequestDto.getContents());
    }

    @Transactional
    public void viewReviewDeep(int reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        ReviewViewed reviewViewed = reviewViewedRepository.findByReviewAndUser(review, user)
                .orElseGet(insertViewType(review, user));
        if (reviewViewed != null) {
            reviewViewed.updateViewType(VIEW_TYPE_DEEP);
        }
    }

    public Supplier<? extends ReviewViewed> insertViewType(Review review, User user) {
        ReviewViewed reviewViewed = ReviewViewed.builder()
                .review(review)
                .user(user)
                .type(VIEW_TYPE_DEEP)
                .build();
        reviewViewedRepository.save(reviewViewed);
        return () -> reviewViewed;
    }
}