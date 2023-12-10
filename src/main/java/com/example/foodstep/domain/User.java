package com.example.foodstep.domain;

import com.example.foodstep.enums.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
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
