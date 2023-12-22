package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;

public interface ReviewRepositoryCustom {
    Review searchById(Integer id);
}
