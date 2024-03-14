package com.demo.playground.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.demo.playground.dto.request.CreateAttractionRequest;
import com.demo.playground.dto.response.AttractionResponse;
import com.demo.playground.entity.Attraction;
import com.demo.playground.type.AttractionType;
import com.demo.playground.mapper.AttractionMapper;
import com.demo.playground.repository.AttractionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
public class AttractionServiceTest {

    @Mock
    private AttractionRepository attractionRepository;

    @Mock
    private AttractionMapper attractionMapper;

    @InjectMocks
    private AttractionService attractionService;

    @Test
    public void testCreateAttraction() {
        CreateAttractionRequest request = CreateAttractionRequest.builder()
                .lat(10.0)
                .lng(20.0)
                .type(AttractionType.TRAMPOLINE)
                .build();

        Attraction attraction = Attraction.builder()
                .id(UUID.randomUUID())
                .latitude(10.0)
                .longitude(20.0)
                .durability(1)
                .type(AttractionType.SWING)
                .build();

        AttractionResponse expectedResponse = AttractionResponse.builder()
                .id(attraction.getId())
                .latitude(10.0)
                .longitude(20.0)
                .durability(1)
                .type(AttractionType.SLIDE)
                .build();

        when(attractionRepository.save(any(Attraction.class))).thenReturn(attraction);
        when(attractionMapper.toAttractionResponse(any(Attraction.class))).thenReturn(expectedResponse);

        AttractionResponse actualResponse = attractionService.createAttraction(request);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getLatitude(), actualResponse.getLatitude());
        assertEquals(expectedResponse.getLongitude(), actualResponse.getLongitude());
        assertEquals(expectedResponse.getDurability(), actualResponse.getDurability());
        assertEquals(expectedResponse.getType(), actualResponse.getType());
    }

    @Test
    public void testGetAllAttractions() {
        List<Attraction> attractionList = new ArrayList<>();
        attractionList.add(Attraction.builder()
                .id(UUID.randomUUID())
                .latitude(10.0)
                .longitude(20.0)
                .durability(1)
                .type(AttractionType.SWING)
                .build());
        attractionList.add(Attraction.builder()
                .id(UUID.randomUUID())
                .latitude(30.0)
                .longitude(40.0)
                .durability(2)
                .type(AttractionType.TRAMPOLINE)
                .build());

        List<AttractionResponse> expectedResponseList = attractionList.stream()
                .map(attraction -> AttractionResponse.builder()
                        .id(attraction.getId())
                        .latitude(attraction.getLatitude())
                        .longitude(attraction.getLongitude())
                        .durability(attraction.getDurability())
                        .type(attraction.getType())
                        .build())
                .collect(Collectors.toList());

        when(attractionRepository.findAll()).thenReturn(attractionList);
        when(attractionMapper.toAttractionResponse(any(Attraction.class)))
                .thenAnswer(invocation -> {
                    Attraction attraction = invocation.getArgument(0);
                    return AttractionResponse.builder()
                            .id(attraction.getId())
                            .latitude(attraction.getLatitude())
                            .longitude(attraction.getLongitude())
                            .durability(attraction.getDurability())
                            .type(attraction.getType())
                            .build();
                });

        List<AttractionResponse> actualResponseList = attractionService.getAllAttractions();

        assertEquals(expectedResponseList.size(), actualResponseList.size());
        for (int i = 0; i < expectedResponseList.size(); i++) {
            AttractionResponse expected = expectedResponseList.get(i);
            AttractionResponse actual = actualResponseList.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getLatitude(), actual.getLatitude());
            assertEquals(expected.getLongitude(), actual.getLongitude());
            assertEquals(expected.getDurability(), actual.getDurability());
            assertEquals(expected.getType(), actual.getType());
        }
    }

    @Test
    public void testGetAttractionById() {
        UUID attractionId = UUID.randomUUID();
        Attraction attraction = Attraction.builder()
                .id(attractionId)
                .latitude(10.0)
                .longitude(20.0)
                .durability(1)
                .type(AttractionType.TRAMPOLINE)
                .build();

        AttractionResponse expectedResponse = AttractionResponse.builder()
                .id(attraction.getId())
                .latitude(attraction.getLatitude())
                .longitude(attraction.getLongitude())
                .durability(attraction.getDurability())
                .type(attraction.getType())
                .build();

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));
        when(attractionMapper.toAttractionResponse(any(Attraction.class))).thenReturn(expectedResponse);

        AttractionResponse actualResponse = attractionService.getAttractionById(attractionId);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getLatitude(), actualResponse.getLatitude());
        assertEquals(expectedResponse.getLongitude(), actualResponse.getLongitude());
        assertEquals(expectedResponse.getDurability(), actualResponse.getDurability());
        assertEquals(expectedResponse.getType(), actualResponse.getType());
    }


}
