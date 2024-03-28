package com.example.foodstep.dto.review;

import com.example.foodstep.domain.*;
import com.example.foodstep.dto.TagDto;
import com.example.foodstep.enums.PlaceCategory;
import com.example.foodstep.util.AddressUtil;
import com.example.foodstep.util.DateUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
// @Builder
@NoArgsConstructor
public class ReviewResponseDto {
    private Integer id;

    private Float rate;

    private String keyword;

    private String contents;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    private String passedTime;

    //User
    private String username;
    private String profileImgUrl;

    //Place
    private String placeName;
    private String placeAddressShort;
    private PlaceCategory placeCategory;
    private Float rateAvg;

    //List
    private final List<String> imagePathList = new ArrayList<>();

    private final List<TagDto> tagList = new ArrayList<>();

    //Count
    private Boolean isLiked;
    private Integer likeCount;
    private Integer commentCount;


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
        rateAvg = review.getPlace().getRateAvg();
    }

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
        // this.imagePathList = imageList;
    }

    @QueryProjection
    public ReviewResponseDto(Review review, User user, Place place, List<ReviewImage> imageList, List<Tag> tags, Boolean liked) {
        id = review.getId();
        rate = review.getRate();
        keyword = review.getKeyword();
        contents = review.getContents();
        dateInit = review.getDateInit();
        dateMod = review.getDateMod();
        likeCount = review.getLikeCount();
        commentCount = review.getCommentCount();
        passedTime = DateUtil.getFeedDate(review.getDateInit());
        username = user.getUsername();
        profileImgUrl = user.getProfileImgUrl();
        placeName = place.getName();
        placeAddressShort = AddressUtil.shortenToSiOrGuAndDong(place.getAddress());
        placeCategory = place.getPlaceCategory();
        rateAvg = place.getRateAvg();
        imageList.forEach(image -> {
            if (image != null && !imagePathList.contains(image.getImagePath())) {  // querydsl Cartesian 곱 방지 (쿼리 로직에 대한 방안 검토..)
                imagePathList.add(image.getImagePath());
            }
        });
        tags.forEach(tag -> {
            if (tag != null) {
                TagDto tagDto = new TagDto(tag.getId(), tag.getName());
                if (!tagList.contains(tagDto)) { // querydsl Cartesian 곱 방지 (쿼리 로직에 대한 방안 검토..)
                    tagList.add(tagDto);
                }
            }
        });
        isLiked = liked;
    }
}
