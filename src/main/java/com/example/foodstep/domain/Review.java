package com.example.foodstep.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

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

    private String recommend;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(updatable = false)
    @CreationTimestamp
    private OffsetDateTime dateInit;

    @UpdateTimestamp
    private OffsetDateTime dateMod;

    @Builder
    public Review(Integer userId, Integer placeId, Float rate, String recommend, String contents, OffsetDateTime dateMod) {
        this.userId = userId;
        this.placeId = placeId;
        this.rate = rate;
        this.recommend = recommend;
        this.contents = contents;
        this.dateMod = dateMod;
    }
}

