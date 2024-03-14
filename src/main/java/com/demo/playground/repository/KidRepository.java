package com.demo.playground.repository;

import com.demo.playground.entity.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KidRepository extends JpaRepository<Kid, UUID> {

}
