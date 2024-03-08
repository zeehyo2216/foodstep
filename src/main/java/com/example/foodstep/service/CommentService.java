package com.example.foodstep.service;

import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.CommentResponseDto;
import com.example.foodstep.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public List<CommentResponseDto> getComment(int reviewId, User loginUser) {
        List<CommentResponseDto> commentList = commentRepository.searchByReviewId(reviewId, loginUser);
        return commentList;
    }

    @Transactional
    public List<CommentResponseDto> getReply(int id, User loginUser) {
        List<CommentResponseDto> replyList = commentRepository.searchReplyById(id, loginUser);
        return replyList;
    }

}
