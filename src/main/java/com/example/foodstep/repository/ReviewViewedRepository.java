package com.example.foodstep.repository;

import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.ReviewViewed;
import com.example.foodstep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewViewedRepository extends JpaRepository<ReviewViewed, Integer> {
    Optional<ReviewViewed> findByReviewAndUser(Review review, User user);
}
