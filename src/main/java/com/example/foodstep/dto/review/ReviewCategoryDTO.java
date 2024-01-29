package com.example.foodstep.dto.review;

import com.example.foodstep.domain.Tag;
import com.example.foodstep.dto.LocationDto;
import com.example.foodstep.enums.OrderByFilter;
import com.example.foodstep.enums.PlaceCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewCategoryDTO {
    private PlaceCategory placeCategory;

    private LocationDto currentLocation;

    private Double distance; // km단위

    private Tag tag;

    private OrderByFilter orderByFilter;

    private Integer pageNumber;
}
