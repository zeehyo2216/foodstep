package com.example.foodstep.dto.user;

import com.example.foodstep.domain.User;
import com.example.foodstep.enums.Authority;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
public class UserDto extends org.springframework.security.core.userdetails.User {
    private User user;
    public UserDto(User user) {
        super(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(Authority.ROLE_USER.toString())));
    }
}
