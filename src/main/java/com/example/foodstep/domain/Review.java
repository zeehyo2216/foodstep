package com.example.foodstep.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "user_id")
    @NotNull
    private Integer userId;

    @NotNull
    //@Column(name = "place_id")
    private Integer placeId;

    @NotNull
    private Float rate;

    @NotNull
    private String recommend;

    //@NotNull
    @ColumnDefault("0")
    private Integer likes;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Review(Integer userId, Integer placeId, Float rate, String recommend, Integer likes, String contents) {
        this.userId = userId;
        this.placeId = placeId;
        this.rate = rate;
        this.recommend = recommend;
        this.likes = likes;
        this.contents = contents;
    }
}

