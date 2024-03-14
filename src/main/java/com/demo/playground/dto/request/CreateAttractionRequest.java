package com.demo.playground.dto.request;

import com.demo.playground.type.AttractionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttractionRequest {
    private double lat;
    private double lng;
    private AttractionType type;
}