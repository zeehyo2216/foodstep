package com.example.foodstep.repository;

import com.example.foodstep.domain.QPlace;
import com.example.foodstep.domain.QReview;
import com.example.foodstep.domain.QUser;
import com.example.foodstep.domain.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Review> searchById(Integer id) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;

        return Optional.ofNullable(queryFactory
                .select(review)
                .from(review)
                .leftJoin(review.user, user).fetchJoin()
                .leftJoin(review.place, place).fetchJoin()
                .distinct()
                .where(review.id.eq(id))
                .fetchOne());
    }
}
