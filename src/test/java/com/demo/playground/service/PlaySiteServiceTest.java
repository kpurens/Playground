package com.demo.playground.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.demo.playground.config.PlaySiteProperties;
import com.demo.playground.dto.request.CreatePlaySiteRequest;
import com.demo.playground.dto.response.PlaySiteResponse;
import com.demo.playground.entity.Attraction;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.exception.PlaySiteAccessException;
import com.demo.playground.mapper.PlaySiteMapper;
import com.demo.playground.repository.PlaySiteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.*;

@SpringBootTest
public class PlaySiteServiceTest {

    @InjectMocks
    private PlaySiteService playSiteService;
    @Mock
    private PlaySiteRepository playSiteRepository;
    @Mock
    private AttractionService attractionService;
    @Mock
    private PlaySiteMapper playSiteMapper;
    @Captor
    private ArgumentCaptor<PlaySite> playSiteCaptor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePlaySite() {
        UUID attractionId = UUID.randomUUID();
        List<UUID> attractionIds = Collections.singletonList(attractionId);
        CreatePlaySiteRequest request = CreatePlaySiteRequest.builder()
                .attractionIds(attractionIds)
                .build();
        Attraction attraction = new Attraction();
        attraction.setId(attractionId);
        Set<Attraction> attractions = Stream.of(attraction).collect(Collectors.toSet());
        PlaySite playSite = PlaySite.builder()
                .attractions(attractions)
                .build();
        PlaySite savedPlaySite = PlaySite.builder()
                .id(UUID.randomUUID())
                .attractions(attractions)
                .build();
        PlaySiteResponse expectedResponse = new PlaySiteResponse();

        when(attractionService.findById(attractionId)).thenReturn(attraction);
        when(playSiteRepository.save(any(PlaySite.class))).thenReturn(savedPlaySite);
        when(playSiteMapper.toPlaySiteResponse(any(PlaySite.class))).thenReturn(expectedResponse);

        PlaySiteResponse response = playSiteService.createPlaySite(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(playSiteRepository).save(playSiteCaptor.capture());
        PlaySite capturedPlaySite = playSiteCaptor.getValue();

        assertNotNull(capturedPlaySite.getAttractions());
        assertFalse(capturedPlaySite.getAttractions().isEmpty());
        assertEquals(attractionId, capturedPlaySite.getAttractions().iterator().next().getId());
    }

    @Test
    public void testGetAllPlaySites() {
        PlaySite playSite1 = new PlaySite();
        PlaySite playSite2 = new PlaySite();
        List<PlaySite> playSites = Arrays.asList(playSite1, playSite2);
        when(playSiteRepository.findAll()).thenReturn(playSites);

        PlaySiteResponse response1 = new PlaySiteResponse();
        PlaySiteResponse response2 = new PlaySiteResponse();
        when(playSiteMapper.toPlaySiteResponse(playSite1)).thenReturn(response1);
        when(playSiteMapper.toPlaySiteResponse(playSite2)).thenReturn(response2);

        List<PlaySiteResponse> responses = playSiteService.getAllPlaySites();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertTrue(responses.contains(response1));
        assertTrue(responses.contains(response2));
    }

    @Test
    public void testGetPlaySiteByIdFound() {
        UUID id = UUID.randomUUID();
        PlaySite playSite = new PlaySite();
        when(playSiteRepository.findById(id)).thenReturn(Optional.of(playSite));

        PlaySiteResponse expectedResponse = new PlaySiteResponse();
        when(playSiteMapper.toPlaySiteResponse(playSite)).thenReturn(expectedResponse);

        PlaySiteResponse response = playSiteService.getPlaySiteById(id);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(playSiteRepository, times(1)).findById(id);
        verify(playSiteMapper, times(1)).toPlaySiteResponse(playSite);
    }

    @Test
    public void testGetPlaySiteByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(playSiteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playSiteService.getPlaySiteById(id));

        verify(playSiteRepository, times(1)).findById(id);
        verify(playSiteMapper, never()).toPlaySiteResponse(any(PlaySite.class));
    }

    @Test
    public void testResetVisitorCount() {
        PlaySite playSite1 = PlaySite.builder().id(UUID.randomUUID()).visitorCount(10).build();
        PlaySite playSite2 = PlaySite.builder().id(UUID.randomUUID()).visitorCount(20).build();

        when(playSiteRepository.findAll()).thenReturn(Arrays.asList(playSite1, playSite2));

        playSiteService.resetVisitorCount();

        verify(playSiteRepository, times(1)).save(playSite1);
        verify(playSiteRepository, times(1)).save(playSite2);

        assert(playSite1.getVisitorCount() == 0);
        assert(playSite2.getVisitorCount() == 0);
    }
}
