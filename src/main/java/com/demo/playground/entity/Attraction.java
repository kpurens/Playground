package com.demo.playground.entity;

import com.demo.playground.type.AttractionType;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {
    @Id
    @UuidGenerator
    private UUID id;
    private double latitude;
    private double longitude;
    private double durability;
    private AttractionType type;

    @ManyToOne
    private PlaySite playSite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attraction that = (Attraction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}