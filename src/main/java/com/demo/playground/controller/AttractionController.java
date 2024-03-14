package com.demo.playground.controller;

import com.demo.playground.dto.request.CreateAttractionRequest;
import com.demo.playground.dto.response.AttractionResponse;
import com.demo.playground.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attractions")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;

    @PostMapping
    public ResponseEntity<AttractionResponse> createAttraction(@RequestBody CreateAttractionRequest request) {
        AttractionResponse response = attractionService.createAttraction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AttractionResponse>> getAllAttractions() {
        List<AttractionResponse> response = attractionService.getAllAttractions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttractionResponse> getAttractionById(@PathVariable UUID id) {
        AttractionResponse response = attractionService.getAttractionById(id);
        return ResponseEntity.ok(response);
    }
}