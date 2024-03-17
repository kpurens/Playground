package com.demo.playground.service;

import com.demo.playground.entity.Kid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Queue;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @Test
    public void testAddKidToPlaySiteQueue() {
        UUID playSiteId = UUID.randomUUID();
        Kid kid = Kid.builder().id(UUID.randomUUID()).name("John").age(10).ticket(1).acceptQueue(true).build();

        queueService.addKidToPlaySiteQueue(playSiteId, kid);
        Queue<Kid> queue = queueService.getPlaySiteQueue(playSiteId);

        assertNotNull(queue);
        assertFalse(queue.isEmpty());
        assertTrue(queue.contains(kid));
        assertTrue(queueService.isKidInAnyQueue(kid));
    }

    @Test
    public void testRemoveKidFromPlaySiteQueue() {
        UUID playSiteId = UUID.randomUUID();
        Kid kid = Kid.builder().id(UUID.randomUUID()).name("Jane").age(8).ticket(2).acceptQueue(true).build();

        queueService.addKidToPlaySiteQueue(playSiteId, kid);
        queueService.removeKidFromPlaySiteQueue(playSiteId, kid);

        Queue<Kid> queue = queueService.getPlaySiteQueue(playSiteId);

        assertNotNull(queue);
        assertFalse(queue.contains(kid));
        assertFalse(queueService.isKidInAnyQueue(kid));
    }

    @Test
    public void testPollPlaySiteQueue() {
        UUID playSiteId = UUID.randomUUID();
        Kid kid = Kid.builder().id(UUID.randomUUID()).name("Josh").age(9).ticket(3).acceptQueue(true).build();

        queueService.addKidToPlaySiteQueue(playSiteId, kid);
        Kid dequeuedKid = queueService.pollPlaySiteQueue(playSiteId);

        assertEquals(kid, dequeuedKid);
        assertFalse(queueService.isKidInAnyQueue(kid));
    }

    @Test
    public void testIsKidInPlaySiteQueue() {
        UUID playSiteId = UUID.randomUUID();
        Kid kid = Kid.builder().id(UUID.randomUUID()).name("Jack").age(7).ticket(4).acceptQueue(true).build();

        queueService.addKidToPlaySiteQueue(playSiteId, kid);
        boolean result = queueService.isKidInPlaySiteQueue(playSiteId, kid);

        assertTrue(result);
    }

    @Test
    public void testIsKidInAnyQueue() {
        UUID playSiteId1 = UUID.randomUUID();
        UUID playSiteId2 = UUID.randomUUID();
        Kid kid1 = Kid.builder().id(UUID.randomUUID()).name("Jim").age(6).ticket(5).acceptQueue(true).build();
        Kid kid2 = Kid.builder().id(UUID.randomUUID()).name("Joe").age(11).ticket(6).acceptQueue(true).build();

        queueService.addKidToPlaySiteQueue(playSiteId1, kid1);
        queueService.addKidToPlaySiteQueue(playSiteId2, kid2);

        assertTrue(queueService.isKidInAnyQueue(kid1));
        assertTrue(queueService.isKidInAnyQueue(kid2));

        queueService.removeKidFromPlaySiteQueue(playSiteId1, kid1);
        assertFalse(queueService.isKidInAnyQueue(kid1));
    }
}
