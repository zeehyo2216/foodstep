package com.example.foodstep.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser; //팔로우 요청자

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser; // 팔로우 받은 자
}
