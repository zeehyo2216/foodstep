package com.example.foodstep.controller;

import com.example.foodstep.dto.PlaceDto;
import com.example.foodstep.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/place")
public class PlaceController {
    private final PlaceService placeService;
    @PostMapping("/add")
    public ResponseEntity<String> addPlace(@RequestBody PlaceDto placeDto) {
        placeService.addPLace(placeDto);
        return ResponseEntity.ok("Place Added");
    }
}
