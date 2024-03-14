package com.demo.playground.controller;

import com.demo.playground.dto.request.CreateKidRequest;
import com.demo.playground.dto.response.KidResponse;
import com.demo.playground.service.KidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kids")
public class KidController {

    @Autowired
    private KidService kidService;

    @PostMapping
    public ResponseEntity<KidResponse> createKid(@RequestBody CreateKidRequest request) {
        KidResponse response = kidService.createKid(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<KidResponse>> getAllKids() {
        List<KidResponse> response = kidService.getAllKids();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KidResponse> getKidById(@PathVariable UUID id) {
        KidResponse response = kidService.getKidById(id);
        return ResponseEntity.ok(response);
    }
}