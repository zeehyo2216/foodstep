package com.example.foodstep.dto.user;

import com.example.foodstep.domain.User;
import com.example.foodstep.enums.Authority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$")
    private String password;

    @NotNull
    private String verifyTypeInfo; //email or phone

    private String name;

    public User toUserEntity(Authority authority, BCryptPasswordEncoder bCryptPasswordEncoder, String verifyType) {
        if (verifyType.equals("email")) {
            return User.builder()
                    .username(username)
                    .password(bCryptPasswordEncoder.encode(password))
                    .email(verifyTypeInfo)
                    .authority(authority)
                    .name(name)
                    .build();
        } else if (verifyType.equals("phone")) {
            return User.builder()
                    .username(username)
                    .password(bCryptPasswordEncoder.encode(password))
                    .phone(verifyTypeInfo)
                    .authority(authority)
                    .name(name)
                    .build();
        } else
            return null;
    }




}