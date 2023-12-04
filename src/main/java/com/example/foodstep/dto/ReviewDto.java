package com.example.foodstep.dto;

import com.example.foodstep.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ReviewDto {
    private Integer id;

    private Integer userId;

    private Integer placeId;

    private Float rate;

    private String recommend;

    private Integer likes;

    private String contents;

    public Review toEntity() {
        return Review.builder().userId(userId)
                .placeId(placeId)
                .rate(rate)
                .recommend(recommend)
                .likes(likes)
                .contents(contents)
                .build();
    }

    public ReviewDto(Review review){
        this.id = review.getId();
        this.userId = review.getUserId();
        this.placeId = review.getPlaceId();
        this.rate = review.getRate();
        this.recommend = review.getRecommend();
        this.likes = review.getLikes();
        this.contents = review.getContents();
    }


}
