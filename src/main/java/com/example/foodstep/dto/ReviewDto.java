package com.example.foodstep.dto;

import com.example.foodstep.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
public class ReviewDto {
    private Integer id;

    private Integer userId;

    private Integer placeId;

    private Float rate;

    private String recommend;

    private String contents;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    public Review toEntity() {
        return Review.builder().userId(userId)
                .placeId(placeId)
                .rate(rate)
                .recommend(recommend)
                .contents(contents)
                .build();
    }

    public ReviewDto(Review review){
        this.id = review.getId();
        this.userId = review.getUserId();
        this.placeId = review.getPlaceId();
        this.rate = review.getRate();
        this.recommend = review.getRecommend();
        this.contents = review.getContents();
        this.dateInit = review.getDateInit();
        this.dateMod = review.getDateMod();
    }


}
