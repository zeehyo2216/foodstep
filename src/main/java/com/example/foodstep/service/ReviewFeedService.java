package com.example.foodstep.service;

import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.event.AddReviewViewEvent;
import com.example.foodstep.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewFeedService {
    private final ReviewRepository reviewRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final Integer PAGE_SIZE = 10; // 발자국 당 이미지 평균 개수 * 10

    @Transactional
    public Slice<ReviewResponseDto> searchFeedListReviews(ReviewCategoryDTO reviewCategoryDTO, User user) {
        int pageNumber;
        if (reviewCategoryDTO.getPageNumber() == null) {
            pageNumber = 0;
        } else {
            pageNumber = reviewCategoryDTO.getPageNumber();
        }

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        Slice<ReviewResponseDto> reviewResponseDtoList = reviewRepository.searchAllByMultipleCategories(reviewCategoryDTO, user, pageable);

        //Add to ReviewViewed(user-review mapped) table with light type.
        eventPublisher.publishEvent(new AddReviewViewEvent(reviewResponseDtoList.getContent(), user));

        return reviewResponseDtoList;
    }

}