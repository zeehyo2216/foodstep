package com.example.foodstep.event;

import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddReviewViewEvent {
    private List<ReviewResponseDto> reviewResponseDtoList;
    private User user;
}
