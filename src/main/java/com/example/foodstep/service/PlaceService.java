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
        Place place = placeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));
        PlaceDto placeDto = new PlaceDto(place);
        return placeDto;
    }

    public void addPLace(PlaceDto placeDto) {
        Place place = placeDto.toEntity();
        placeRepository.save(place);
    }
}
