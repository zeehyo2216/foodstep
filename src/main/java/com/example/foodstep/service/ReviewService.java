package com.example.foodstep.service;

import com.example.foodstep.domain.Place;
import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.ReviewViewed;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewAddRequestDto;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewDto;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.PlaceRepository;
import com.example.foodstep.repository.ReviewRepository;
import com.example.foodstep.repository.ReviewViewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.foodstep.enums.ErrorCode.PLACE_NOT_FOUND;
import static com.example.foodstep.enums.ErrorCode.REVIEW_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final ReviewViewedRepository reviewViewedRepository;

    private static final Integer PAGE_SIZE = 10;

    public List<ReviewDto> findAllReviews() {
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        List<Review> reviewList = reviewRepository.findAll();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    @Transactional
    public ReviewResponseDto findReviewDetail(int id){
        Review review = reviewRepository.searchById(id).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return reviewResponseDto;
    }

    @Transactional
    public Slice<ReviewResponseDto> searchFeedListReviews(ReviewCategoryDTO reviewCategoryDTO, User user) {
        Pageable pageable = PageRequest.of(reviewCategoryDTO.getPageNumber(), PAGE_SIZE);
        Slice<ReviewResponseDto> reviewResponseDtoList = reviewRepository.searchAllByMultipleCategories(reviewCategoryDTO, pageable);

        //User-Review Map
        List<ReviewViewed> reviewViewedList = new ArrayList<>();
        for (ReviewResponseDto reviewResponseDto : reviewResponseDtoList) {
            reviewResponseDto.getId();
        }

        // reviewViewedRepository.saveAll();

        return reviewResponseDtoList;
    }

    @Transactional
    public void addReview(ReviewAddRequestDto reviewAddRequestDto, User user) {
        ReviewDto reviewDto = new ReviewDto(reviewAddRequestDto);
        reviewDto.setUser(user);

        Place place = placeRepository.findById(reviewAddRequestDto.getPlaceId()).orElseThrow(() -> new CustomException(PLACE_NOT_FOUND));
        reviewDto.setPlace(place);

        Review review = reviewDto.toEntity();
        reviewRepository.save(review);
    }

    @Transactional
    public void editReview(ReviewDto reviewDto){
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
        review.updateReview(reviewDto.getRate(), reviewDto.getKeyword(), reviewDto.getContents());
    }
}