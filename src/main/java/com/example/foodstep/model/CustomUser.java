package com.example.foodstep.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class CustomUser extends User {
    private com.example.foodstep.domain.User user;
    public CustomUser(com.example.foodstep.domain.User user) {
        super(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getAuthority().toString())));
        this.user = user;
    }
}
