package com.example.foodstep.domain;

import com.example.foodstep.enums.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @NotNull
    @Column(updatable = false, unique = true)
    private String username;

    @NotNull
    private String password;

    @Column(unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    //private String uuid;

    private String name;


    @Builder
    public User(String username, String password, String email, String phone, String name, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.authority = authority;
        this.name = name;
    }


}
