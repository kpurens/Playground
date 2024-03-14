package com.demo.playground.service;

import com.demo.playground.dto.request.CreateKidRequest;
import com.demo.playground.dto.response.KidResponse;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.mapper.KidMapper;
import com.demo.playground.repository.KidRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class KidService {

    @Autowired
    private KidRepository kidRepository;

    @Autowired
    private KidMapper kidMapper;

    public KidResponse createKid(CreateKidRequest createKidRequest) {
        Kid kid = Kid.builder()
                .age(createKidRequest.getAge())
                .name(createKidRequest.getName())
                .acceptQueue(createKidRequest.isAcceptQueue())
                .ticket(-1)
                .build();
        kid = kidRepository.save(kid);
        return kidMapper.toKidResponse(kid);
    }

    public List<KidResponse> getAllKids() {
        List<Kid> kids = kidRepository.findAll();
        return kids.stream()
                .map(kidMapper::toKidResponse)
                .collect(Collectors.toList());
    }

    public KidResponse getKidById(UUID id) {
        Kid kid = findById(id);
        return kidMapper.toKidResponse(kid);
    }

    public Kid findById(UUID kidId) {
        return kidRepository.findById(kidId)
                .orElseThrow(() -> new EntityNotFoundException("Kid not found with ID: " + kidId));
    }

    public void updatePlaySite(Kid kid, PlaySite newPlaySite) {
        kid.setPlaySite(newPlaySite);
        kidRepository.save(kid);
    }
}