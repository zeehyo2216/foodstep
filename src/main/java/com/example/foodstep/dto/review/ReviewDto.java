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

    private String keyword;

    private String contents;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    public Review toEntity() {
        return Review.builder().user(user)
                .place(place)
                .rate(rate)
                .keyword(keyword)
                .contents(contents)
                .build();
    }

    public ReviewDto(Review review){
        this.id = review.getId();
        this.user = review.getUser();
        this.place = review.getPlace();
        this.rate = review.getRate();
        this.keyword = review.getKeyword();
        this.contents = review.getContents();
        this.dateInit = review.getDateInit();
        this.dateMod = review.getDateMod();
    }

    public ReviewDto(ReviewAddRequestDto reviewAddRequestDto) {
        this.rate = reviewAddRequestDto.getRate();
        this.keyword = reviewAddRequestDto.getKeyword();
        this.contents = reviewAddRequestDto.getContents();
    }


}
