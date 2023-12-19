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
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    //private String uuid;

    //private String name;

    //private String phone;

    @Builder
    public User(String email, String password, String nickname, Authority authority) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }


}
