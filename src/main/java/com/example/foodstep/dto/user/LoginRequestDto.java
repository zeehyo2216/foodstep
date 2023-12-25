package com.example.foodstep.dto.user;

import com.example.foodstep.enums.LoginType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private LoginType loginType;
}
