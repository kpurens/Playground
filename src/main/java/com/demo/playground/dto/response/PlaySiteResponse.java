package com.demo.playground.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaySiteResponse {
    private UUID id;
    private int visitorCount;
    private float utilization;

    private List<UUID> kidIds;
    private List<UUID> kidQueue;
    private List<UUID> attractionIds;
}