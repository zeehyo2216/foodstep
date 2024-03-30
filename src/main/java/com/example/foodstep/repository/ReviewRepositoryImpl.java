package com.example.foodstep.repository;

import com.example.foodstep.domain.*;
import com.example.foodstep.dto.LocationDto;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.enums.OrderByFilter;
import com.example.foodstep.enums.PlaceCategory;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    // findById 는 join을 걸지 않고, id로 join table 에 접근하여 N+1 문제 발생하기 때문에
    // querydsl 로 leftjoin 처리를 해준것이다.
    @Override
    public Optional<Review> searchById(Integer id) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;

        return Optional.ofNullable(queryFactory
                .selectFrom(review)
                .leftJoin(review.user, user).fetchJoin()
                .leftJoin(review.place, place).fetchJoin()
                .distinct()
                .where(review.id.eq(id))
                .fetchOne());
    }

    @Override
    public Slice<ReviewResponseDto> searchAllByMultipleCategories(ReviewCategoryDTO reviewCategoryDTO, User viewUser, Pageable pageable) { // Main Feed Algorithm
        QReview review = QReview.review;
        QPlace place = QPlace.place;
        QUser user = QUser.user;
        QReviewViewed reviewViewed = QReviewViewed.reviewViewed;
        QReviewImage reviewImage = QReviewImage.reviewImage;
        QReviewTagMap reviewTagMap = QReviewTagMap.reviewTagMap;
        QTag tag = QTag.tag;
        QLikes likes = QLikes.likes;

        List<Integer> reviewIdList = queryFactory
                .select(review.id)
                .from(review)
                .leftJoin(review.user, user)
                .leftJoin(review.place, place)
                .where(
                        //no-offset 일단 보류

                        // 카테고리 필터
                        placeCategoryEq(reviewCategoryDTO.getPlaceCategory()),
                        distanceLoe(reviewCategoryDTO.getDistance(), reviewCategoryDTO.getCurrentLocation()),

                        // 공통 적용 필터
                        review.contents.length().goe(30),
                        review.rate.goe(3),
                        // review.imageList.size().gt(1)

                        //조회
                        JPAExpressions
                                .selectOne()
                                .from(reviewViewed)
                                .where(
                                        reviewViewed.review.eq(review),
                                        reviewViewed.user.eq(viewUser)
                                )
                                .notExists()
                )
                .orderBy(review.id.desc())
                // .orderBy(orderByFilterToSpecifiers(reviewCategoryDTO.getOrderByFilter(), reviewCategoryDTO.getCurrentLocation()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        // 조회수 급증한 피드에 대해서는 따로 reviewIdList 에 임의로 random 한순서로 2개정도씩 집어넣는건?? (여기에 로직 추가)

        List<ReviewResponseDto> contents = queryFactory
                .selectFrom(review)
                .leftJoin(review.user, user)
                .leftJoin(review.place, place)
                .leftJoin(review.imageList, reviewImage)
                .leftJoin(review.tagMapList, reviewTagMap)
                .leftJoin(reviewTagMap.tag, tag)
                .leftJoin(likes).on(review.eq(likes.review).and(likes.user.eq(viewUser)))
                .where(
                        review.id.in(reviewIdList)
                )
                .orderBy(review.id.desc())
                .transform(groupBy(review.id).list(
                        Projections.constructor(ReviewResponseDto.class,
                                review, user, place, list(reviewImage), list(tag), likes.user.isNull().not())
                ));

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<ReviewResponseDto> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }

    public BooleanExpression placeCategoryEq(PlaceCategory placeCategory) {
        QPlace place = QPlace.place;

        if (placeCategory != null) {
            return place.placeCategory.eq(placeCategory);
        }
        return null;
    }

    public BooleanExpression distanceLoe(Double distance, LocationDto currentLocation) {
        QReview review = QReview.review;
        QPlace place = QPlace.place;

        if (distance != null) {
            NumberTemplate<Double> distanceExpression = Expressions.numberTemplate(Double.class,
                    "6371 * acos(cos(radians({0})) * cos(radians({2})) * cos(radians({3}) - radians({1})) + sin(radians({0})) * sin(radians({2})))",
                    currentLocation.getLatitude(), currentLocation.getLongitude(), review.place.latitude, review.place.longitude);
            return distanceExpression.loe(distance);
        }
        return null;
    }

    public OrderSpecifier[] orderByRecommendation(OrderByFilter orderByFilter, LocationDto currentLocation) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        QReview review = QReview.review;

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }




        //홈(피드) 탭에서는 제외, 검색 탭에서 사용
    public OrderSpecifier[] orderByFilterToSpecifiers(OrderByFilter orderByFilter, LocationDto currentLocation) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        QReview review = QReview.review;

        switch (orderByFilter) {
            case RECENT:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));
            case NEAREST:
                NumberTemplate<Double> distanceExpression = Expressions.numberTemplate(Double.class,
                        "6371 * acos(cos(radians({0})) * cos(radians({2})) * cos(radians({3}) - radians({1})) + sin(radians({0})) * sin(radians({2})))",
                        currentLocation.getLatitude(), currentLocation.getLongitude(), review.place.latitude, review.place.longitude);

                orderSpecifiers.add(new OrderSpecifier(Order.ASC, distanceExpression));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));
                break;
            case RATE_HIGH:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.rate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));
            default: // DEFAULT enum
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.id));

        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
