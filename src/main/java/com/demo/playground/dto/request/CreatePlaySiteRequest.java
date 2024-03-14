package com.demo.playground.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class CreatePlaySiteRequest {
    @NotEmpty(message = "List of UUIDs must not be empty")
    private List<UUID> attractionIds;
}