package com.example.foodstep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NearPlaceRequestDto {
    private Double currentX;
    private Double currentY;
}
