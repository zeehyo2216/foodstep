package com.example.foodstep.domain;

import jakarta.persistence.Entity;
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
public class Place extends BaseEntity{
    @NotNull
    private String name;

    @NotNull
    private String address;

    private String addressRoad;

    private String phone;

    @NotNull
    private Double coorX;

    @NotNull
    private Double coorY;

    private Double rateAvg;

    private String description;

    private String notification;


    @Builder
    public Place(String name, String address, String addressRoad, String phone, Double coorX, Double coorY) {
        this.name = name;
        this.address = address;
        this.addressRoad = addressRoad;
        this.phone = phone;
        this.coorX = coorX;
        this.coorY = coorY;
    }


}
