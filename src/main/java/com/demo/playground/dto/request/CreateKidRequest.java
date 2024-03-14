package com.demo.playground.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateKidRequest {
    private int age;
    private String name;
    private boolean acceptQueue;
}