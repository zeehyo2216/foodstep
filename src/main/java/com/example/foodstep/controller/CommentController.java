package com.example.foodstep.controller;

import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.CommentResponseDto;
import com.example.foodstep.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/list/{id}")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable(name = "id") int id, @RequestUser User user) {
        return new ResponseEntity<>(commentService.getComment(id, user), HttpStatus.OK);
    }

    @GetMapping("/replies/{id}")
    public ResponseEntity<List<CommentResponseDto>> getReplyList(@PathVariable(name = "id") int id, @RequestUser User user) {
        return new ResponseEntity<>(commentService.getReply(id, user), HttpStatus.OK);
    }
}
