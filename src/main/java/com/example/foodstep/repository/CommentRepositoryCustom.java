package com.example.foodstep.repository;

import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponseDto> searchByReviewId(Integer id, User loginUser);
    List<CommentResponseDto> searchReplyById(Integer id, User loginUser);

}
