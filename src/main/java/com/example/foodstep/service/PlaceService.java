package com.example.foodstep.service;

import com.example.foodstep.domain.Place;
import com.example.foodstep.dto.PlaceDto;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.foodstep.enums.ErrorCode.PLACE_EXISTS;
import static com.example.foodstep.enums.ErrorCode.PLACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceDto findPlaceDetail(int id) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new CustomException(PLACE_NOT_FOUND));
        PlaceDto placeDto = new PlaceDto(place);
        return placeDto;
    }

    public void addPLace(PlaceDto placeDto) {

        if (placeRepository.existsByNameAndAddress(placeDto.getName(), placeDto.getAddress())) {
            throw new CustomException(PLACE_EXISTS);
        }

        Place place = placeDto.toEntity();
        placeRepository.save(place);
    }
}
