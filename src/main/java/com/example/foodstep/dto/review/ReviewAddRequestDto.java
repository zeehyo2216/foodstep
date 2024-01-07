package com.example.foodstep.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewAddRequestDto {

    @NotNull
    private int placeId;

    @NotNull
    private float rate;

    private String keyword;

    private String contents;
}
