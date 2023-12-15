package com.example.foodstep.service;

import com.example.foodstep.domain.Place;
import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.ReviewDto;
import com.example.foodstep.dto.ReviewRequestDto;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.PlaceRepository;
import com.example.foodstep.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.foodstep.enums.ErrorCode.PLACE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public List<ReviewDto> findAllReviews() {
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        List<Review> reviewList =  reviewRepository.findAll();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public ReviewDto findReviewDetail(int id) throws NoSuchElementException {
        Review review = reviewRepository.findById(id).orElseThrow();
        ReviewDto reviewDto = new ReviewDto(review);
        return reviewDto;
    }

    @Transactional
    public void addReview(ReviewRequestDto reviewRequestDto, User user) {
        ReviewDto reviewDto = new ReviewDto(reviewRequestDto);
        reviewDto.setUser(user);

        Place place = placeRepository.findById(reviewRequestDto.getPlaceId()).orElseThrow(() -> new CustomException(PLACE_NOT_FOUND));
        reviewDto.setPlace(place);

        Review review = reviewDto.toEntity();
        reviewRepository.save(review);
    }

    @Transactional
    public void editReview(ReviewDto reviewDto) throws NoSuchElementException {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow();
        review.updateReview(reviewDto.getRate(), reviewDto.getRecommend(), reviewDto.getContents());
    }
}