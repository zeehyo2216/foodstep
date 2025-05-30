package com.example.foodstep.domain;

import com.example.foodstep.enums.PlaceCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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
public class Place extends BaseEntity{
    @NotNull
    private String name;

    @NotNull
    private String address;

    private String addressRoad;

    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PlaceCategory placeCategory;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    private Float rateAvg;

    private String description;

    private String notification;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();


    @Builder
    public Place(String name, String address, String addressRoad, String phone, PlaceCategory placeCategory, Double longitude, Double latitude) {
        this.name = name;
        this.address = address;
        this.addressRoad = addressRoad;
        this.phone = phone;
        this.placeCategory = placeCategory;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getReviewCount() {
        return reviews.size();
    }


}
