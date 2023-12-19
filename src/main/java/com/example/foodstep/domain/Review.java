package com.example.foodstep.domain;

import jakarta.persistence.Column;
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
public class Review extends BaseEntity{

    //@Column(name = "user_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    //@Column(name = "place_id")
    private Place place;

    @NotNull
    private Float rate;

    private String keyword;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Review(User user, Place place, Float rate, String keyword, String contents) {
        this.user = user;
        this.place = place;
        this.rate = rate;
        this.keyword = keyword;
        this.contents = contents;
    }


    public void updateReview(Float rate, String keyword, String contents){
        this.rate = rate;
        this.keyword = keyword;
        this.contents = contents;
    }
}

