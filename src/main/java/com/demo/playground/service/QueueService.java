package com.demo.playground.service;

import com.demo.playground.entity.Kid;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QueueService {
    private final Map<UUID, Queue<Kid>> playSiteQueues = new ConcurrentHashMap<>();
    private final HashSet<Kid> kidsInQueue = new HashSet<>();

    public void addKidToPlaySiteQueue(UUID playSiteId, Kid kid) {
        playSiteQueues.computeIfAbsent(playSiteId, k -> new LinkedList<>()).add(kid);
        kidsInQueue.add(kid);
    }

    public void removeKidFromPlaySiteQueue(UUID playSiteId, Kid kid) {
        Queue<Kid> queue = playSiteQueues.get(playSiteId);
        if (queue != null) {
            queue.remove(kid);
            kidsInQueue.remove(kid);
        }
    }

    public Kid pollPlaySiteQueue(UUID playSiteId) {
        Queue<Kid> queue = playSiteQueues.get(playSiteId);
        if (queue != null) {
            Kid kid = queue.poll();
            kidsInQueue.remove(kid);
            return kid;
        }
        return null;
    }

    public Queue<Kid> getPlaySiteQueue(UUID playSiteId) {
        return playSiteQueues.get(playSiteId);
    }

    public boolean isKidInPlaySiteQueue(UUID playSiteId, Kid kid) {
        Queue<Kid> queue = playSiteQueues.get(playSiteId);
        return queue != null && queue.contains(kid);
    }

    public boolean isKidInAnyQueue(Kid kid) {
        return kidsInQueue.contains(kid);
    }
}
