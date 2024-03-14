package com.demo.playground.entity;

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
public class Kid {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    private int age;
    private int ticket;
    private boolean acceptQueue;

    @ManyToOne
    private PlaySite playSite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kid that = (Kid) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}