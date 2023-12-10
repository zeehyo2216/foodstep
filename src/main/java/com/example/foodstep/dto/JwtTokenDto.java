package com.example.foodstep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtTokenDto {
    private String grantType;

    private String accessToken;

    private String refreshToken;
}
