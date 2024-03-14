package com.demo.playground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaySite {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToMany(mappedBy = "playSite", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attraction> attractions;

    @OneToMany(mappedBy = "playSite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Kid> kids;

    @Transient
    private final Queue<Kid> waitingQueue = new LinkedList<>();

    public void enqueueKid(Kid kid) {
        waitingQueue.add(kid);
    }

    public Kid dequeueKid() {
        return waitingQueue.poll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaySite playSite = (PlaySite) o;
        return Objects.equals(id, playSite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}