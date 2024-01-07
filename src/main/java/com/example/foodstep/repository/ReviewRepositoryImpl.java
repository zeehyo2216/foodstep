package com.example.foodstep.repository;

import com.example.foodstep.domain.QPlace;
import com.example.foodstep.domain.QReview;
import com.example.foodstep.domain.QUser;
import com.example.foodstep.domain.Review;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.enums.OrderByFilter;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Slice<Review> searchAllByMultipleCategories(ReviewCategoryDTO categoryRequestDTO) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;

        List<Review> reviews = queryFactory
                .select(review)
                .from(review)
                .leftJoin(review.user, user).fetchJoin()
                .leftJoin(review.place, place).fetchJoin()
                .distinct()
                .where(
                        review.id.eq(1)
                )
                .orderBy(getOrderByExpression(categoryRequestDTO.getOrderByFilter()))
                .fetch();

        return new SliceImpl<>(reviews);
    }

    public OrderSpecifier[] getOrderByExpression(OrderByFilter orderByFilter) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        switch (orderByFilter) {
            case RECENT:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, QReview.review.dateInit));
            case NEAREST:
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, QReview.review.dateInit));

        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
