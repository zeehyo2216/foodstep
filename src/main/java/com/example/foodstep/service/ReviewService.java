package com.example.foodstep.service;

import com.example.foodstep.domain.Place;
import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.ReviewViewed;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewRequestDto;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.event.AddReviewViewEvent;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.PlaceRepository;
import com.example.foodstep.repository.ReviewRepository;
import com.example.foodstep.repository.ReviewViewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

import static com.example.foodstep.enums.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final ReviewViewedRepository reviewViewedRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final Integer PAGE_SIZE = 10;
    private static final Integer VIEW_TYPE_LIGHT = 1;
    private static final Integer VIEW_TYPE_DEEP = 2;

    @Transactional
    public ReviewResponseDto findReviewDetail(int id){
        Review review = reviewRepository.searchById(id).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return reviewResponseDto;
    }

    @Transactional
    public Slice<ReviewResponseDto> searchFeedListReviews(ReviewCategoryDTO reviewCategoryDTO, User user) {
        Pageable pageable = PageRequest.of(reviewCategoryDTO.getPageNumber(), PAGE_SIZE);
        Slice<ReviewResponseDto> reviewResponseDtoList = reviewRepository.searchAllByMultipleCategories(reviewCategoryDTO, user, pageable);

        //Add to ReviewViewed(user-review mapped) table with light type.
        eventPublisher.publishEvent(new AddReviewViewEvent(reviewResponseDtoList.getContent(), user));

        return reviewResponseDtoList;
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