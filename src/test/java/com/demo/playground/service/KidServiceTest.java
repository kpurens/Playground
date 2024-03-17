package com.demo.playground.service;

import com.demo.playground.dto.request.CreateKidRequest;
import com.demo.playground.dto.response.KidResponse;
import com.demo.playground.entity.Kid;
import com.demo.playground.mapper.KidMapper;
import com.demo.playground.repository.KidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KidServiceTest {

    @Mock
    private KidRepository kidRepository;

    @Mock
    private KidMapper kidMapper;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private KidService kidService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createKid() {

        CreateKidRequest createKidRequest = CreateKidRequest.builder()
                .age(10)
                .name("Test Kid")
                .acceptQueue(true)
                .build();

        Kid savedKid = Kid.builder()
                .id(java.util.UUID.randomUUID())
                .age(10)
                .name("Test Kid")
                .acceptQueue(true)
                .ticket(-1)
                .build();

        KidResponse kidResponse = KidResponse.builder()
                .id(savedKid.getId())
                .age(10)
                .name("Test Kid")
                .acceptQueue(true)
                .ticket(-1)
                .playSiteId(null)
                .build();

        when(kidRepository.save(any(Kid.class))).thenReturn(savedKid);
        when(kidMapper.toKidResponse(any(Kid.class))).thenReturn(kidResponse);

        KidResponse response = kidService.createKid(createKidRequest);

        assertEquals(kidResponse.getId(), response.getId());
        assertEquals(kidResponse.getName(), response.getName());
        assertEquals(kidResponse.getAge(), response.getAge());
        assertEquals(kidResponse.isAcceptQueue(), response.isAcceptQueue());
        assertEquals(kidResponse.getTicket(), response.getTicket());
    }

    @Test
    void getAllKids() {
        List<Kid> kids = new ArrayList<>();
        Kid kid1 = Kid.builder()
                .id(java.util.UUID.randomUUID())
                .age(10)
                .name("Kid One")
                .acceptQueue(true)
                .ticket(-1)
                .build();

        Kid kid2 = Kid.builder()
                .id(java.util.UUID.randomUUID())
                .age(8)
                .name("Kid Two")
                .acceptQueue(false)
                .ticket(-1)
                .build();

        kids.add(kid1);
        kids.add(kid2);

        KidResponse kidResponse1 = KidResponse.builder()
                .id(kid1.getId())
                .age(kid1.getAge())
                .name(kid1.getName())
                .acceptQueue(kid1.isAcceptQueue())
                .ticket(kid1.getTicket())
                .playSiteId(null)
                .build();

        KidResponse kidResponse2 = KidResponse.builder()
                .id(kid2.getId())
                .age(kid2.getAge())
                .name(kid2.getName())
                .acceptQueue(kid2.isAcceptQueue())
                .ticket(kid2.getTicket())
                .playSiteId(null)
                .build();

        List<KidResponse> kidResponses = new ArrayList<>();
        kidResponses.add(kidResponse1);
        kidResponses.add(kidResponse2);

        when(kidRepository.findAll()).thenReturn(kids);
        when(kidMapper.toKidResponse(kid1)).thenReturn(kidResponse1);
        when(kidMapper.toKidResponse(kid2)).thenReturn(kidResponse2);

        List<KidResponse> responses = kidService.getAllKids();

        assertEquals(2, responses.size());
        assertEquals(kidResponses.get(0).getId(), responses.get(0).getId());
        assertEquals(kidResponses.get(0).getName(), responses.get(0).getName());
        assertEquals(kidResponses.get(0).getAge(), responses.get(0).getAge());
        assertEquals(kidResponses.get(1).getId(), responses.get(1).getId());
        assertEquals(kidResponses.get(1).getName(), responses.get(1).getName());
        assertEquals(kidResponses.get(1).getAge(), responses.get(1).getAge());
    }

    @Test
    void getKidById() {
        UUID kidId = java.util.UUID.randomUUID();
        Kid kid = Kid.builder()
                .id(kidId)
                .age(10)
                .name("Test Kid")
                .acceptQueue(true)
                .ticket(-1)
                .build();

        KidResponse kidResponse = KidResponse.builder()
                .id(kidId)
                .age(10)
                .name("Test Kid")
                .acceptQueue(true)
                .ticket(-1)
                .playSiteId(null)
                .build();

        when(kidRepository.findById(kidId)).thenReturn(java.util.Optional.of(kid));
        when(kidMapper.toKidResponse(kid)).thenReturn(kidResponse);

        KidResponse response = kidService.getKidById(kidId);

        assertEquals(kidResponse.getId(), response.getId());
        assertEquals(kidResponse.getName(), response.getName());
        assertEquals(kidResponse.getAge(), response.getAge());
        assertEquals(kidResponse.isAcceptQueue(), response.isAcceptQueue());
        assertEquals(kidResponse.getTicket(), response.getTicket());
    }


}
