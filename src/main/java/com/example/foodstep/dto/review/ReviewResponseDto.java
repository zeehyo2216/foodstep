package com.example.foodstep.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class ReviewResponseDto {
    //Review
    private Integer id;

    private Float rate;

    private String recommend;

    private String contents;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    //User
    private String nickname;

    //Place
    private String placeName;
    private String placeAddress;
    private String category;



}
