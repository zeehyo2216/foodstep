package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    Optional<Review> searchById(Integer id);
}
