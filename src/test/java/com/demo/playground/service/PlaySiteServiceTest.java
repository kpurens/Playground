package com.demo.playground.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.demo.playground.config.PlaySiteProperties;
import com.demo.playground.dto.request.CreatePlaySiteRequest;
import com.demo.playground.dto.response.PlaySiteResponse;
import com.demo.playground.entity.Attraction;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.mapper.PlaySiteMapper;
import com.demo.playground.repository.PlaySiteRepository;
import com.demo.playground.type.AttractionType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class PlaySiteServiceTest {

    @InjectMocks
    private PlaySiteService playSiteService;
    @Mock
    private KidService kidService;
    @Mock
    private QueueService queueService;
    @Mock
    private PlaySiteRepository playSiteRepository;
    @Mock
    private AttractionService attractionService;
    @Mock
    private PlaySiteMapper playSiteMapper;
    @Mock
    private PlaySiteProperties playSiteProperties;
    @Captor
    private ArgumentCaptor<PlaySite> playSiteCaptor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePlaySite() {
        UUID attractionId = UUID.randomUUID();
        Attraction attraction = new Attraction();
        attraction.setId(attractionId);
        Set<Attraction> attractions = Collections.singleton(attraction);
        PlaySite savedPlaySite = PlaySite.builder()
                .id(UUID.randomUUID())
                .attractions(attractions)
                .build();
        PlaySiteResponse expectedResponse = new PlaySiteResponse();

        when(attractionService.findById(attractionId)).thenReturn(attraction);
        when(playSiteRepository.save(any(PlaySite.class))).thenReturn(savedPlaySite);
        when(playSiteMapper.toPlaySiteResponse(any(PlaySite.class))).thenReturn(expectedResponse);

        CreatePlaySiteRequest request = CreatePlaySiteRequest.builder()
                .attractionIds(Collections.singletonList(attractionId))
                .build();

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
    public void testCreatePlaySiteEntityNotFound() {
        UUID attractionId = UUID.randomUUID();
        when(attractionService.findById(attractionId)).thenThrow(new EntityNotFoundException("Attraction not found"));

        CreatePlaySiteRequest request = CreatePlaySiteRequest.builder()
                .attractionIds(Collections.singletonList(attractionId))
                .build();

        assertThrows(EntityNotFoundException.class, () -> playSiteService.createPlaySite(request),
                "Expected createPlaySite to throw EntityNotFoundException but it didn't");
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

    @Test
    public void testAddKidToPlaySite() {
        UUID playSiteId = UUID.randomUUID();
        UUID kidId = UUID.randomUUID();

        Kid kid = Kid.builder()
                .id(kidId)
                .name("Josh")
                .age(10)
                .ticket(654321)
                .acceptQueue(false)
                .build();

        PlaySite playSite = PlaySite.builder()
                .id(playSiteId)
                .visitorCount(0)
                .attractions(new HashSet<>())
                .kids(new HashSet<>())
                .build();

        when(playSiteProperties.getMaximumKids()).thenReturn(4);
        when(playSiteRepository.findById(playSiteId)).thenReturn(Optional.of(playSite));
        when(kidService.findById(kidId)).thenReturn(kid);
        when(playSiteRepository.save(playSite)).thenReturn(playSite);
        when(playSiteMapper.toPlaySiteResponse(playSite)).thenReturn(new PlaySiteResponse());

        PlaySiteResponse response = playSiteService.addKidToPlaySite(playSiteId, kidId);

        assertNotNull(response);
        verify(kidService, times(1)).updatePlaySite(kid, playSite);
        verify(playSiteRepository, times(1)).save(playSite);
        assertTrue(playSite.getKids().contains(kid), "Kid should be added to the play site.");
    }

    @Test
    public void testRemoveKidFromPlaySite() {
        UUID playSiteId = UUID.randomUUID();
        UUID kidId = UUID.randomUUID();

        Kid kid = Kid.builder()
                .id(kidId)
                .name("John")
                .age(10)
                .ticket(123456)
                .acceptQueue(true)
                .playSite(new PlaySite())
                .build();

        PlaySite playSite = PlaySite.builder()
                .id(playSiteId)
                .visitorCount(1)
                .attractions(new HashSet<>())
                .kids(new HashSet<>(Collections.singletonList(kid)))
                .build();

        when(playSiteRepository.findById(playSiteId)).thenReturn(Optional.of(playSite));
        when(kidService.findById(kidId)).thenReturn(kid);
        when(playSiteMapper.toPlaySiteResponse(playSite)).thenReturn(new PlaySiteResponse());

        assertTrue(playSite.getKids().contains(kid));

        PlaySiteResponse response = playSiteService.removeKidFromPlaySite(playSiteId, kidId);

        assertNotNull(response);
        verify(kidService, times(1)).updatePlaySite(kid, null);
        assertFalse(playSite.getKids().contains(kid), "Kid should be removed from the play site.");
    }


    @Test
    public void testAddAttractionToPlaySite() {
        UUID playSiteId = UUID.randomUUID();
        UUID attractionId = UUID.randomUUID();

        PlaySite playSite = PlaySite.builder()
                .id(playSiteId)
                .visitorCount(10)
                .attractions(new HashSet<>())
                .build();

        Attraction attraction = Attraction.builder()
                .id(attractionId)
                .latitude(12.34)
                .longitude(56.78)
                .durability(10.0)
                .type(AttractionType.BALL_PIT)
                .build();

        PlaySiteResponse expectedResponse = PlaySiteResponse.builder()
                .id(playSiteId)
                .visitorCount(10)
                .utilization(0.5f)
                .kidIds(new ArrayList<>())
                .kidQueue(new ArrayList<>())
                .attractionIds(Arrays.asList(attractionId))
                .build();

        when(playSiteRepository.findById(playSiteId)).thenReturn(Optional.of(playSite));
        when(attractionService.findById(attractionId)).thenReturn(attraction);
        when(playSiteRepository.save(any(PlaySite.class))).thenReturn(playSite);
        when(playSiteMapper.toPlaySiteResponse(playSite)).thenReturn(expectedResponse);

        PlaySiteResponse response = playSiteService.addAttractionToPlaySite(playSiteId, attractionId);

        verify(playSiteRepository).save(playSite);
        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getVisitorCount(), response.getVisitorCount());
        assertEquals(expectedResponse.getUtilization(), response.getUtilization());
        assertEquals(expectedResponse.getAttractionIds(), response.getAttractionIds());
    }

    @Test
    public void testRemoveAttractionFromPlaySite() {
        UUID playSiteId = UUID.randomUUID();
        UUID attractionId = UUID.randomUUID();

        Attraction attraction = Attraction.builder()
                .id(attractionId)
                .latitude(12.34)
                .longitude(56.78)
                .durability(10.0)
                .type(AttractionType.CAROUSEL)
                .build();

        Set<Attraction> attractions = new HashSet<>();
        attractions.add(attraction);

        PlaySite playSite = PlaySite.builder()
                .id(playSiteId)
                .visitorCount(10)
                .attractions(attractions)
                .build();

        PlaySiteResponse expectedResponse = PlaySiteResponse.builder()
                .id(playSiteId)
                .visitorCount(10)
                .utilization(0.5f)
                .kidIds(new ArrayList<>())
                .kidQueue(new ArrayList<>())
                .attractionIds(new ArrayList<>())
                .build();

        when(playSiteRepository.findById(playSiteId)).thenReturn(Optional.of(playSite));
        when(playSiteRepository.save(any(PlaySite.class))).thenReturn(playSite);
        when(playSiteMapper.toPlaySiteResponse(playSite)).thenReturn(expectedResponse);

        PlaySiteResponse response = playSiteService.removeAttractionFromPlaySite(playSiteId, attractionId);

        assertNotNull(response);
        verify(playSiteRepository).save(playSite);
        assertFalse(response.getAttractionIds().contains(attractionId));
        assertEquals(expectedResponse, response);
    }
}
