package com.demo.playground.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KidResponse {
    private UUID id;
    private String name;
    private int age;
    private int ticket;
    private boolean acceptQueue;
    private UUID playSiteId;
}