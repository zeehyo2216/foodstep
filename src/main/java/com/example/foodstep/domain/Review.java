package com.example.foodstep.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Review extends BaseEntity{

    //@Column(name = "user_id")
    @NotNull
    private Integer userId;

    @NotNull
    //@Column(name = "place_id")
    private Integer placeId;

    @NotNull
    private Float rate;

    private String recommend;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Review(Integer userId, Integer placeId, Float rate, String recommend, String contents) {
        this.userId = userId;
        this.placeId = placeId;
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

