package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserIdAndStartDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Event> findAllEventsByServiceEntity_Id(Long serviceId);

//    Long user(User user);
}
