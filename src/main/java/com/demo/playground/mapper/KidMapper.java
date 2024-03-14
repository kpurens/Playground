package com.demo.playground.mapper;

import com.demo.playground.dto.response.KidResponse;
import com.demo.playground.dto.response.PlaySiteResponse;
import com.demo.playground.entity.Attraction;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.PlaySite;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class KidMapper {

    public KidResponse toKidResponse(Kid kid) {
        KidResponse response = KidResponse.builder()
                .id(kid.getId())
                .name(kid.getName())
                .age(kid.getAge())
                .ticket(kid.getTicket())
                .acceptQueue(kid.isAcceptQueue())
                .playSiteId(kid.getPlaySite() != null ? kid.getPlaySite().getId() : null)
                .build();

        return response;
    }
}
