package com.example.foodstep.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationCodeRequestDto {
    private String email;
    private String verificationCode;
}
