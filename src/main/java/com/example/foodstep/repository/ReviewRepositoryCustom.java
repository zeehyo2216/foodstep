package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewResponseDto;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {
    Optional<Review> searchById(Integer id);

    List<ReviewResponseDto> searchAllByMultipleCategories(ReviewCategoryDTO categoryRequestDTO);
}
