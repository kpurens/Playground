package com.demo.playground.service;

import com.demo.playground.dto.request.CreateAttractionRequest;
import com.demo.playground.dto.response.AttractionResponse;
import com.demo.playground.entity.Attraction;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.mapper.AttractionMapper;
import com.demo.playground.repository.AttractionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private AttractionMapper attractionMapper;

    public AttractionResponse createAttraction(CreateAttractionRequest createAttractionRequest) {
        Attraction attraction = Attraction.builder()
                .latitude(createAttractionRequest.getLat())
                .longitude(createAttractionRequest.getLng())
                .type(createAttractionRequest.getType())
                .durability(1)
                .build();
        attraction = attractionRepository.save(attraction);
        return attractionMapper.toAttractionResponse(attraction);
    }

    public List<AttractionResponse> getAllAttractions() {
        List<Attraction> attractions = attractionRepository.findAll();
        return attractions.stream()
                .map(attractionMapper::toAttractionResponse)
                .collect(Collectors.toList());
    }

    public AttractionResponse getAttractionById(UUID id) {
        Attraction attraction = findById(id);
        return attractionMapper.toAttractionResponse(attraction);
    }

    public Attraction findById(UUID attractionId) {
        return attractionRepository.findById(attractionId)
                .orElseThrow(() -> new EntityNotFoundException("Attraction not found with ID: " + attractionId));
    }

    public void updatePlaySite(Attraction attraction, PlaySite newPlaySite) {
        attraction.setPlaySite(newPlaySite);
        attractionRepository.save(attraction);
    }
}