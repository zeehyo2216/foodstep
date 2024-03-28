package com.example.foodstep.dto.review;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.foodstep.dto.review.QReviewResponseDto is a Querydsl Projection type for ReviewResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewResponseDto extends ConstructorExpression<ReviewResponseDto> {

    private static final long serialVersionUID = -1914728590L;

    public QReviewResponseDto(com.querydsl.core.types.Expression<? extends com.example.foodstep.domain.Review> review, com.querydsl.core.types.Expression<? extends com.example.foodstep.domain.User> user, com.querydsl.core.types.Expression<? extends com.example.foodstep.domain.Place> place, com.querydsl.core.types.Expression<? extends java.util.List<com.example.foodstep.domain.ReviewImage>> imageList, com.querydsl.core.types.Expression<? extends java.util.List<com.example.foodstep.domain.Tag>> tags, com.querydsl.core.types.Expression<Boolean> liked) {
        super(ReviewResponseDto.class, new Class<?>[]{com.example.foodstep.domain.Review.class, com.example.foodstep.domain.User.class, com.example.foodstep.domain.Place.class, java.util.List.class, java.util.List.class, boolean.class}, review, user, place, imageList, tags, liked);
    }

}

