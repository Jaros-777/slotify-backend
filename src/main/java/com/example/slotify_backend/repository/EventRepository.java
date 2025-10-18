package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOwnerIdAndStartDateBetween(Long ownerId, LocalDateTime startDate, LocalDateTime endDate);
}
