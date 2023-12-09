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
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
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

    private String recommend;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Review(User user, Place place, Float rate, String recommend, String contents) {
        this.user = user;
        this.place = place;
        this.rate = rate;
        this.recommend = recommend;
        this.contents = contents;
    }


    public void updateReview(Float rate, String recommend, String contents){
        this.rate = rate;
        this.recommend = recommend;
        this.contents = contents;
    }
}

