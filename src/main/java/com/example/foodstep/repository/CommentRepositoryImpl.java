package com.example.foodstep.repository;

import com.example.foodstep.domain.Comment;
import com.example.foodstep.domain.QComment;
import com.example.foodstep.domain.QUser;
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
    public List<Comment> searchByReviewId(Integer reviewId) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return queryFactory
                .selectFrom(comment)
                .leftJoin(comment.user, user).fetchJoin()
                .distinct()
                .where(
                        comment.review.id.eq(reviewId),
                        comment.parentComment.isNull()
                )
                .fetch();
    }

}
