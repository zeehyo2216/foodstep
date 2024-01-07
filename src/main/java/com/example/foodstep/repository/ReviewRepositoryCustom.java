package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    Optional<Review> searchById(Integer id);

    Slice<Review> searchAllByMultipleCategories(ReviewCategoryDTO categoryRequestDTO);
}
