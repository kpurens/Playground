package com.demo.playground.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TicketService {
    private final AtomicInteger counter = new AtomicInteger(0);

    public int getNextTicketNumber() {
        return counter.incrementAndGet();
    }
}
