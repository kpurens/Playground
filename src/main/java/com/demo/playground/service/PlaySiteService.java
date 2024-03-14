package com.demo.playground.service;

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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import java.util.stream.Collectors;

@Service
public class PlaySiteService {

    @Autowired
    private PlaySiteRepository playSiteRepository;

    @Autowired
    private KidService kidService;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private PlaySiteMapper playSiteMapper;

    @Autowired
    private PlaySiteProperties playSiteProperties;

    public PlaySiteResponse createPlaySite(CreatePlaySiteRequest createPlaySiteRequest) {
        PlaySite playSite = new PlaySite();
        Set<Attraction> attractions = createPlaySiteRequest.getAttractionIds().stream()
                .map(attractionService::findById)
                .collect(Collectors.toSet());

        playSite.setAttractions(attractions);
        playSite = playSiteRepository.save(playSite);
        return playSiteMapper.toPlaySiteResponse(playSite);
    }

    public List<PlaySiteResponse> getAllPlaySites() {
        List<PlaySite> playSites = playSiteRepository.findAll();
        return playSites.stream()
                .map(playSiteMapper::toPlaySiteResponse)
                .collect(Collectors.toList());
    }

    public PlaySiteResponse getPlaySiteById(UUID id) {
        PlaySite playSite = findById(id);
        return playSiteMapper.toPlaySiteResponse(playSite);
    }

    @Transactional
    public PlaySiteResponse addKidToPlaySite(UUID id, UUID kidId) {
        PlaySite playSite = findById(id);
        Kid kid = kidService.findById(kidId);

        if (kid.getPlaySite() != null) {
            throw new PlaySiteAccessException("Kid is already in a play site");
        }

        if (hasReachedMaxKids(playSite)) {
            if (!kid.isAcceptQueue()) {
                throw new PlaySiteAccessException("Play site is full and kid does not accept queue");
            }
        }

        kidService.updatePlaySite(kid, playSite);
        playSite.getKids().add(kid);
        playSite = playSiteRepository.save(playSite);
        return playSiteMapper.toPlaySiteResponse(playSite);
    }

    @Transactional
    public PlaySiteResponse removeKidFromPlaySite(UUID id, UUID kidId) {
        PlaySite playSite = findById(id);
        Kid kid = findKidById(playSite, kidId);
        playSite.getAttractions().remove(kid);
        kidService.updatePlaySite(kid, null);
        playSite = playSiteRepository.save(playSite);
        return playSiteMapper.toPlaySiteResponse(playSite);
    }

    @Transactional
    public PlaySiteResponse addAttractionToPlaySite(UUID id, UUID attractionId) {
        PlaySite playSite = findById(id);
        Attraction attraction = attractionService.findById(attractionId);
        attractionService.updatePlaySite(attraction, playSite);
        playSite.getAttractions().add(attraction);
        playSite = playSiteRepository.save(playSite);
        return playSiteMapper.toPlaySiteResponse(playSite);
    }

    @Transactional
    public PlaySiteResponse removeAttractionFromPlaySite(UUID id, UUID attractionId) {
        PlaySite playSite = findById(id);
        Attraction attraction = findAttractionById(playSite, attractionId);
        playSite.getAttractions().remove(attraction);
        attractionService.updatePlaySite(attraction, null);
        playSite = playSiteRepository.save(playSite);
        return playSiteMapper.toPlaySiteResponse(playSite);
    }

    @Transactional
    public void resetVisitorCount() {
        List<PlaySite> playSites = playSiteRepository.findAll();
        playSites.forEach(playSite -> {
            playSite.setVisitorCount(0);
            playSiteRepository.save(playSite);
        });
    }

    private boolean hasReachedMaxKids(PlaySite playSite) {
        return playSite.getKids().size() >= playSiteProperties.getMaximumKids();
    }

    private PlaySite findById(UUID playSiteId) {
        return playSiteRepository.findById(playSiteId)
                .orElseThrow(() -> new EntityNotFoundException("PlaySite not found with ID: " + playSiteId));
    }

    private Kid findKidById(PlaySite playSite, UUID kidId) {
        for (Kid kid : playSite.getKids()) {
            if (kid.getId().equals(kidId)) {
                return kid;
            }
        }
        throw new EntityNotFoundException("Kid not found with id: " + kidId);
    }

    private Attraction findAttractionById(PlaySite playSite, UUID attractionId) {
        for (Attraction attraction : playSite.getAttractions()) {
            if (attraction.getId().equals(attractionId)) {
                return attraction;
            }
        }
        throw new EntityNotFoundException("Attraction not found with id: " + attractionId);
    }
}
