package com.example.foodstep.dto.review;

import com.example.foodstep.domain.Comment;
import com.example.foodstep.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class CommentResponseDto {
    private Integer id;

    private String contents;

    private Boolean hasLiked;

    private Integer likeCount;

    private Integer replyCount;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    //User
    private String username;

    private String profileImgUrl;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.profileImgUrl = comment.getUser().getProfileImgUrl();
        this.contents = comment.getContents();
        this.likeCount = comment.getLikeCount();
        this.replyCount = comment.getReplyCount();
    }

    public CommentResponseDto(Comment comment, User user, Boolean hasLiked) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.hasLiked = hasLiked;
        this.likeCount = comment.getLikeCount();
        this.replyCount = comment.getReplyCount();
        this.dateInit = comment.getDateInit();
        this.dateMod = comment.getDateMod();
        this.username = user.getUsername();
        this.profileImgUrl = user.getProfileImgUrl();

    }
}
