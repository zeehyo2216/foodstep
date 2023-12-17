package com.example.foodstep.dto.review;

import com.example.foodstep.domain.Place;
import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
public class ReviewDto {
    private Integer id;

    private User user;

    private Place place;

    private Float rate;

    private String recommend;

    private String contents;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    public Review toEntity() {
        return Review.builder().user(user)
                .place(place)
                .rate(rate)
                .recommend(recommend)
                .contents(contents)
                .build();
    }

    public ReviewDto(Review review){
        this.id = review.getId();
        this.user = review.getUser();
        this.place = review.getPlace();
        this.rate = review.getRate();
        this.recommend = review.getRecommend();
        this.contents = review.getContents();
        this.dateInit = review.getDateInit();
        this.dateMod = review.getDateMod();
    }

    public ReviewDto(ReviewRequestDto reviewRequestDto) {
        this.rate = reviewRequestDto.getRate();
        this.recommend = reviewRequestDto.getRecommend();
        this.contents = reviewRequestDto.getContents();
    }


}
