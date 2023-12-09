package com.example.foodstep.service;

import com.example.foodstep.domain.Review;
import com.example.foodstep.dto.ReviewDto;
import com.example.foodstep.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

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
    public void addReview(ReviewDto reviewDto) {
        Review review = reviewDto.toEntity();
        reviewRepository.save(review);
    }

    @Transactional
    public void editReview(ReviewDto reviewDto) throws NoSuchElementException {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow();
        review.updateReview(reviewDto.getRate(), reviewDto.getRecommend(), reviewDto.getContents());
    }
}