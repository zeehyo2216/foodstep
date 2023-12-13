package com.example.foodstep.dto.user;

import com.example.foodstep.domain.User;
import com.example.foodstep.enums.Authority;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserDto {
    private Integer id;
    private String email;
    private String password;
    private String nickname;
    private Authority authority;
    private OffsetDateTime dateInit;
    private OffsetDateTime dateMod;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.authority = user.getAuthority();
        this.dateInit = user.getDateInit();
        this.dateMod = user.getDateMod();
    }

}
