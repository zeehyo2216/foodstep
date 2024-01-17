package com.example.foodstep.repository;

import com.example.foodstep.domain.QPlace;
import com.example.foodstep.domain.QReview;
import com.example.foodstep.domain.QUser;
import com.example.foodstep.domain.Review;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.enums.OrderByFilter;
import com.example.foodstep.enums.PlaceCategory;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public Slice<ReviewResponseDto> searchAllByMultipleCategories(ReviewCategoryDTO reviewCategoryDTO, Pageable pageable) { // Main Feed Algorithm
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;

        List<ReviewResponseDto> contents = queryFactory
                .select(Projections.constructor(ReviewResponseDto.class,
                        review.id,
                        review.rate,
                        review.keyword,
                        review.contents,
                        review.dateInit,
                        review.dateMod,
                        user.username,
                        user.profileImgUrl,
                        place.name,
                        place.address,
                        place.placeCategory
                        ))
                .from(review)
                .leftJoin(review.user, user)
                .leftJoin(review.place, place)
                .where(
                        //no-offset 일단 보류
                        placeCategoryEq(reviewCategoryDTO.getPlaceCategory())

                )
                .orderBy(orderByFilterToSpecifiers(reviewCategoryDTO.getOrderByFilter()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<ReviewResponseDto> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }

    public OrderSpecifier[] orderByFilterToSpecifiers(OrderByFilter orderByFilter) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        QReview review = QReview.review;

        switch (orderByFilter) {
            case RECENT:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));
            case NEAREST:
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, review.place.longitude));
            case RATE_HIGH:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.rate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));
            default: // DEFAULT enum
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));

        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    public BooleanExpression placeCategoryEq(PlaceCategory placeCategory) {
        QPlace place = QPlace.place;

        if (placeCategory != null) {
            return place.placeCategory.eq(placeCategory);
        }
        return null;
    }

}
