package com.example.foodstep.dto.review;

import com.example.foodstep.domain.Comment;
import com.example.foodstep.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CommentResponseDto {
    private Integer id;

    private User user;

    private String contents;

    private Boolean hasLiked;

    private Integer likeCount;

    private List<CommentResponseDto> replyList;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.user = comment.getUser();
        this.contents = comment.getContents();

    }
}
