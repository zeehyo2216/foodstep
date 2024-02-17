package com.example.foodstep.dto.review;

import com.example.foodstep.domain.Review;
import com.example.foodstep.domain.Tag;
import com.example.foodstep.enums.PlaceCategory;
import com.example.foodstep.util.AddressUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewResponseDto {
    //Review

    private Integer id;

    private Float rate;

    private String keyword;

    private String contents;

    private List<String> imageList;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    //User
    private String username;
    private String profileImgUrl;

    //Place
    private String placeName;
    private String placeAddressShort;
    private PlaceCategory placeCategory;
    private Float rateAvg;

    //Tag
    private List<Tag> tagList;

    public ReviewResponseDto(Review review) {
        id = review.getId();
        rate = review.getRate();
        keyword = review.getKeyword();
        contents = review.getContents();
        // imagePath = review.getImagePath();
        dateInit = review.getDateInit();
        dateMod = review.getDateMod();
        username = review.getUser().getUsername();
        profileImgUrl = review.getUser().getProfileImgUrl();
        placeName = review.getPlace().getName();
        placeAddressShort = AddressUtil.shortenToSiOrGuAndDong(review.getPlace().getAddress());
        placeCategory = review.getPlace().getPlaceCategory();
        rateAvg = review.getPlace().getRateAvg();
    }

    // @QueryProjection
    public ReviewResponseDto(Integer id, Float rate, String keyword, String contents,
                             OffsetDateTime dateInit,  OffsetDateTime dateMod, String username, String profileImgUrl,
                             String placeName, String placeAddressShort, PlaceCategory placeCategory, Float rateAvg,
                             List<String> imageList) {
        this.id = id;
        this.rate = rate;
        this.keyword = keyword;
        this.contents = contents;
        this.dateInit = dateInit;
        this.dateMod = dateMod;
        this.username = username;
        this.profileImgUrl = profileImgUrl;
        this.placeName = placeName;
        this.placeAddressShort = AddressUtil.shortenToSiOrGuAndDong(placeAddressShort);
        this.placeCategory = placeCategory;
        this.rateAvg = rateAvg;
        this.imageList = imageList;
    }
}
