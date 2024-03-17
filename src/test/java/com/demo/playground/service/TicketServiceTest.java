package com.demo.playground.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @BeforeEach
    public void setUp() {
        this.ticketService = new TicketService();
    }

    @Test
    public void testGetNextTicketNumber() {
        int firstNumber = ticketService.getNextTicketNumber();
        int secondNumber = ticketService.getNextTicketNumber();
        int thirdNumber = ticketService.getNextTicketNumber();

        assertEquals(1, firstNumber, "The first number should be 1 (since it's reset in setUp).");
        assertEquals(firstNumber + 1, secondNumber, "The second number should be one more than the first.");
        assertEquals(secondNumber + 1, thirdNumber, "The third number should be one more than the second.");
    }
}