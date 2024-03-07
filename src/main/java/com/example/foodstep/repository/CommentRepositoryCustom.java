package com.example.foodstep.repository;

import com.example.foodstep.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> searchByReviewId(Integer id);

}
