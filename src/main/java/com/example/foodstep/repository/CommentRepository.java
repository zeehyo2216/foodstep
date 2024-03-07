package com.example.foodstep.repository;

import com.example.foodstep.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom {
}
