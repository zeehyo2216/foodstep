package com.example.foodstep.service;

import com.example.foodstep.domain.*;
import com.example.foodstep.dto.review.ReviewRequestDto;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

import static com.example.foodstep.enums.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final ReviewViewedRepository reviewViewedRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

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

    @Transactional
    public void likeReview(int reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        Optional<Likes> check = likeRepository.findByReviewAndUser(review, user);

        if (check.isEmpty()) {
            Likes likes = Likes.builder().review(review).user(user).build();
            likeRepository.save(likes);

            review.updateLikeCount(review.getLikeCount() + 1);
        } else {
            throw new CustomException(LIKE_EXISTS);
        }
    }

    @Transactional
    public void unlikeReview(int reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        Likes likes = likeRepository.findByReviewAndUser(review, user).orElseThrow(() -> new CustomException(LIKES_NOT_FOUND));
        likeRepository.delete(likes);

        review.updateLikeCount(review.getLikeCount() - 1);
    }
}