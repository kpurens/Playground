package com.demo.playground.mapper;

import com.demo.playground.dto.response.AttractionResponse;
import com.demo.playground.dto.response.KidResponse;
import com.demo.playground.entity.Attraction;
import com.demo.playground.entity.Kid;
import org.springframework.stereotype.Component;

@Component
public class AttractionMapper {

    public AttractionResponse toAttractionResponse(Attraction attraction) {
        AttractionResponse response = AttractionResponse.builder()
                .id(attraction.getId())
                .latitude(attraction.getLatitude())
                .longitude(attraction.getLongitude())
                .durability(attraction.getDurability())
                .type(attraction.getType())
                .build();

        return response;
    }
}
