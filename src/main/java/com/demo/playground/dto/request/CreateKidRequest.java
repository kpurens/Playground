package com.demo.playground.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateKidRequest {
    @Min(1)
    private int age;

    @NotNull
    @Size(min = 3)
    private String name;

    private boolean acceptQueue;
}