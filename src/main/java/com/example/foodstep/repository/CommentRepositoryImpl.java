package com.example.foodstep.repository;

import com.example.foodstep.domain.*;
import com.example.foodstep.dto.review.CommentResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /*
    findById 는 join을 걸지 않고, id로 join table 에 접근하여 N+1 문제 발생하기 때문에
    querydsl 로 leftjoin 처리를 해준것이다.

    v2 에서는 Slice+Pageable로 무한스크롤 기능 적용하기
    */

    @Override
    public List<CommentResponseDto> searchByReviewId(Integer reviewId, User loginUser) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;
        QCommentLikes commentLikes = QCommentLikes.commentLikes;

        BooleanExpression hasLiked = commentLikes.comment.id.eq(comment.id)
                .and(commentLikes.user.id.eq(loginUser.getId()));

        return queryFactory
                .select(Projections.constructor(
                        CommentResponseDto.class,
                        comment,
                        user,
                        commentLikes.id.isNotNull()
                ))
                .from(comment)
                .leftJoin(commentLikes).on(hasLiked)
                .leftJoin(comment.user, user).fetchJoin()
                .distinct()
                .where(
                        comment.review.id.eq(reviewId),
                        comment.parentComment.isNull()
                )
                .fetch();
    }

    @Override
    public List<CommentResponseDto> searchReplyById(Integer parentId, User loginUser) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;
        QCommentLikes commentLikes = QCommentLikes.commentLikes;

        BooleanExpression hasLiked = commentLikes.comment.id.eq(comment.id)
                .and(commentLikes.user.id.eq(loginUser.getId()));

        return queryFactory
                .select(Projections.constructor(
                        CommentResponseDto.class,
                        comment,
                        user,
                        commentLikes.id.isNotNull()
                ))
                .from(comment)
                .leftJoin(commentLikes).on(hasLiked)
                .leftJoin(comment.user, user).fetchJoin()
                .distinct()
                .where(
                        comment.parentComment.id.eq(parentId)
                )
                .fetch();
    }

}
