package com.example.foodstep.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewViewed extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @NotNull
    private Integer type; // 피드에서 보기: 1, 눌러서 보기: 2

    @Builder
    public ReviewViewed(User user, Review review, Integer type) {
        this.user = user;
        this.review = review;
        this.type = type;
    }
}