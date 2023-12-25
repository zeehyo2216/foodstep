package com.example.foodstep.dto.review;

import com.example.foodstep.domain.Review;
import com.example.foodstep.enums.PlaceCategory;
import com.example.foodstep.util.AddressUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class ReviewResponseDto {
    //Review
    private Integer id;

    private Float rate;

    private String keyword;

    private String contents;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    //User
    private String username;
    private String profileImgUrl;

    //Place
    private String placeName;
    private String placeAddressShort;
    private PlaceCategory placeCategory;

    public ReviewResponseDto(Review review) {
        id = review.getId();
        rate = review.getRate();
        keyword = review.getKeyword();
        contents = review.getContents();
        dateInit = review.getDateInit();
        dateMod = review.getDateMod();
        username = review.getUser().getUsername();
        profileImgUrl = review.getUser().getProfileImgUrl();
        placeName = review.getPlace().getName();
        placeAddressShort = AddressUtil.shortenToSiOrGuAndDong(review.getPlace().getAddress());
        placeCategory = review.getPlace().getPlaceCategory();
    }



}
