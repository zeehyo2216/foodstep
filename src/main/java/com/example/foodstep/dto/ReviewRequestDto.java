package com.example.foodstep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequestDto {

    private int placeId;

    private Float rate;

    private String recommend;

    private String contents;
}
