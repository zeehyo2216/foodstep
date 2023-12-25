package com.example.foodstep.dto.user;

import com.example.foodstep.domain.User;
import com.example.foodstep.enums.Authority;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private Authority authority;
    private OffsetDateTime dateInit;
    private OffsetDateTime dateMod;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.name = user.getName();
        this.authority = user.getAuthority();
        this.dateInit = user.getDateInit();
        this.dateMod = user.getDateMod();
    }

}
