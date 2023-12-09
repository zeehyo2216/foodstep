package com.example.foodstep.controller;

import com.example.foodstep.domain.Place;
import com.example.foodstep.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    public ResponseEntity<Place> findPlaceDetail() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
