package com.example.foodstep.dto;

import com.example.foodstep.domain.Place;
import com.example.foodstep.enums.PlaceCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PlaceDto {
    private Integer id;

    private String name;

    private String address;

    private String addressRoad;

    private String phone;

    private PlaceCategory placeCategory;

    private Double coorX;

    private Double coorY;

    private Double rateAvg;

    private String description;

    private String notification;

    private OffsetDateTime dateInit;

    private OffsetDateTime dateMod;

    public Place toEntity() {
        return Place.builder()
                .name(name)
                .address(address)
                .addressRoad(addressRoad)
                .phone(phone)
                .placeCategory(placeCategory)
                .coorX(coorX)
                .coorY(coorY)
                .build();

    }

    public PlaceDto(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address= place.getAddress();
        this.addressRoad = place.getAddressRoad();
        this.phone = place.getPhone();
        this.placeCategory = place.getPlaceCategory();
        this.coorX = place.getCoorX();
        this.coorY = place.getCoorY();
        this.rateAvg = place.getRateAvg();
        this.description = place.getDescription();
        this.notification = place.getNotification();
        this.dateInit = place.getDateInit();
        this.dateMod = place.getDateMod();

    }

}
