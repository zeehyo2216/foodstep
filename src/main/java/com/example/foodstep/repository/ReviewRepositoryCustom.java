package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    Optional<Review> searchById(Integer id);

    Slice<ReviewResponseDto> searchAllByMultipleCategories(ReviewCategoryDTO categoryRequestDTO, Pageable pageable);
}
