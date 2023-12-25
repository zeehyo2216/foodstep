package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryCustom {

}
