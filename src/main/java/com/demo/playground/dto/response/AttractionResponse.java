package com.demo.playground.dto.response;

import com.demo.playground.type.AttractionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionResponse {
    private UUID id;
    private double latitude;
    private double longitude;
    private double durability;
    private AttractionType type;
}