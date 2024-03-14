package com.demo.playground.mapper;

import com.demo.playground.config.PlaySiteProperties;
import com.demo.playground.dto.response.PlaySiteResponse;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PlaySiteMapper {

    @Autowired
    private PlaySiteProperties playSiteProperties;

    public PlaySiteResponse toPlaySiteResponse(PlaySite playSite) {
        PlaySiteResponse response = new PlaySiteResponse();

        response.setId(playSite.getId());
        response.setVisitorCount(playSite.getVisitorCount());
        response.setUtilization(calculateUtilization(playSite));
        response.setKidIds(convertKidsToIds(playSite.getKids()));
        response.setKidQueue(convertQueueToIds(playSite.getWaitingQueue()));
        response.setAttractionIds(convertAttractionsToIds(playSite.getAttractions()));

        return response;
    }

    private List<UUID> convertKidsToIds(Set<Kid> kids) {
        if (kids == null) {
            return null;
        }
        return kids.stream()
                .map(Kid::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> convertQueueToIds(Queue<Kid> queue) {
        if (queue == null) {
            return null;
        }
        return queue.stream()
                .map(Kid::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> convertAttractionsToIds(Set<Attraction> attractions) {
        if (attractions == null) {
            return null;
        }
        return attractions.stream()
                .map(Attraction::getId)
                .collect(Collectors.toList());
    }

    private float calculateUtilization(PlaySite playSite) {
        int maxKids = playSiteProperties.getMaximumKids();
        return ((float) playSite.getKids().size() / maxKids) * 100;
    }
}
