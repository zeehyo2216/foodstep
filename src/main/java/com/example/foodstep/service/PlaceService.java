package com.example.foodstep.service;

import com.example.foodstep.domain.Place;
import com.example.foodstep.dto.PlaceDto;
import com.example.foodstep.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceDto findPlaceDetail(int id) {
        Place place = placeRepository.findById(id).orElseGet(Place::new);
        PlaceDto placeDto = new PlaceDto(place);
        return placeDto;
    }

    public void addPLace(PlaceDto placeDto) {
        Place place = placeDto.toEntity();
        placeRepository.save(place);
    }
}
