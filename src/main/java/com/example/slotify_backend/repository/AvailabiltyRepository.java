package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabiltyRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findAllByUserId(Long userId);
    Availability findById(Long id);
}
