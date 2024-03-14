package com.demo.playground.controller;

import com.demo.playground.dto.request.CreatePlaySiteRequest;
import com.demo.playground.dto.response.PlaySiteResponse;
import com.demo.playground.service.PlaySiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/play-sites")
public class PlaySiteController {

    @Autowired
    private PlaySiteService playSiteService;

    @PostMapping
    public ResponseEntity<PlaySiteResponse> createPlaySite(@RequestBody CreatePlaySiteRequest createPlaySiteRequest) {
        PlaySiteResponse response = playSiteService.createPlaySite(createPlaySiteRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PlaySiteResponse>> getAllPlaySites() {
        List<PlaySiteResponse> response = playSiteService.getAllPlaySites();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaySiteResponse> getPlaySiteById(@PathVariable UUID id) {
        PlaySiteResponse response = playSiteService.getPlaySiteById(id);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/add-kid/{kidId}")
    public ResponseEntity<PlaySiteResponse> addKidToPlaySite(@PathVariable UUID id, @PathVariable UUID kidId) {
        PlaySiteResponse response = playSiteService.addKidToPlaySite(id, kidId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/remove-kid/{kidId}")
    public ResponseEntity<PlaySiteResponse> removeKidFromPlaySite(@PathVariable UUID id, @PathVariable UUID kidId) {
        PlaySiteResponse response = playSiteService.removeKidFromPlaySite(id, kidId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/add-attraction/{attractionId}")
    public ResponseEntity<PlaySiteResponse> addAttractionToPlaySite(@PathVariable UUID id, @PathVariable UUID attractionId) {
        PlaySiteResponse response = playSiteService.addAttractionToPlaySite(id, attractionId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/remove-attraction/{attractionId}")
    public ResponseEntity<PlaySiteResponse> removeAttractionFromPlaySite(@PathVariable UUID id, @PathVariable UUID attractionId) {
        PlaySiteResponse response = playSiteService.removeAttractionFromPlaySite(id, attractionId);
        return ResponseEntity.ok(response);
    }
}