package com.demo.playground.mapper;

import com.demo.playground.dto.response.KidResponse;
import com.demo.playground.entity.Kid;
import org.springframework.stereotype.Component;

@Component
public class KidMapper {

    public KidResponse toKidResponse(Kid kid) {
        return KidResponse.builder()
                .id(kid.getId())
                .name(kid.getName())
                .age(kid.getAge())
                .ticket(kid.getTicket())
                .acceptQueue(kid.isAcceptQueue())
                .playSiteId(kid.getPlaySite() != null ? kid.getPlaySite().getId() : null)
                .build();
    }
}
