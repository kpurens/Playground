package com.demo.playground.mapper;

import com.demo.playground.config.PlaySiteProperties;
import com.demo.playground.dto.response.PlaySiteResponse;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.Attraction;
import com.demo.playground.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PlaySiteMapper {

    @Autowired
    private PlaySiteProperties playSiteProperties;

    @Autowired
    private QueueService queueService;

    public PlaySiteResponse toPlaySiteResponse(PlaySite playSite) {
        PlaySiteResponse response = new PlaySiteResponse();

        response.setId(playSite.getId());
        response.setVisitorCount(playSite.getVisitorCount());
        response.setUtilization(calculateUtilization(playSite));
        response.setKidIds(convertKidsToIds(playSite.getKids()));
        response.setKidQueue(convertQueueToIds(queueService.getPlaySiteQueue(playSite.getId())));
        response.setAttractionIds(convertAttractionsToIds(playSite.getAttractions()));

        return response;
    }

    private List<UUID> convertKidsToIds(Set<Kid> kids) {
        if (kids == null)
            return new ArrayList<>();

        return kids.stream()
                .map(Kid::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> convertQueueToIds(Queue<Kid> queue) {
        if (queue == null)
            return new ArrayList<>();

        return queue.stream()
                .map(Kid::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> convertAttractionsToIds(Set<Attraction> attractions) {
        if (attractions == null)
            return new ArrayList<>();

        return attractions.stream()
                .map(Attraction::getId)
                .collect(Collectors.toList());
    }

    private float calculateUtilization(PlaySite playSite) {
        int maxKids = playSiteProperties.getMaximumKids();
        return ((float) playSite.getKidCount() / maxKids) * 100;
    }
}