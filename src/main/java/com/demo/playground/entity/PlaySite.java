package com.demo.playground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaySite {
    @Id
    @UuidGenerator
    private UUID id;
    private int visitorCount;

    @OneToMany(mappedBy = "playSite", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attraction> attractions;

    @OneToMany(mappedBy = "playSite", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Kid> kids;

    public boolean isKidInPlaySite(Kid kid) {
        return kids.contains(kid);
    }

    public void addKid(Kid kid) {
        kids.add(kid);
        visitorCount++;
    }

    public int getKidCount() {
        return kids == null ? 0 : kids.size();
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