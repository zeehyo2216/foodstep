package com.example.foodstep.event;

import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.ReviewViewed;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.ReviewRepository;
import com.example.foodstep.repository.ReviewViewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.foodstep.enums.ErrorCode.REVIEW_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AddReviewViewEventListener {

    private final ReviewRepository reviewRepository;
    private final ReviewViewedRepository reviewViewedRepository;
    private static final Integer VIEW_TYPE_LIGHT = 1;
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addReviewViewed(AddReviewViewEvent event) {
        List<ReviewViewed> reviewViewedList = new ArrayList<>();
        for (ReviewResponseDto reviewResponseDto : event.getReviewResponseDtoList()) {
            Review review = reviewRepository.findById(reviewResponseDto.getId())
                    .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
            ReviewViewed reviewViewed = ReviewViewed.builder()
                    .review(review)
                    .user(event.getUser())
                    .type(VIEW_TYPE_LIGHT)
                    .build();
            reviewViewedList.add(reviewViewed);
        }
        reviewViewedRepository.saveAll(reviewViewedList);
    }
}
