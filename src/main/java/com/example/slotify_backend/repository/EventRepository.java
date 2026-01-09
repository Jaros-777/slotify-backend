package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserIdAndStartDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Event> findAllEventsByServiceEntity_Id(Long serviceId);
    List<Event> findAllByClientId(Long clientId);
    List<Event> findAllByServiceEntityIdAndStartDateBetween(Long serviceId, LocalDateTime startDate, LocalDateTime endDate);
    List<Event> findAllByUserIdAndBookingStatusNot(Long userId, BookingStatus bookingStatus);

    List<Event> findAllByUserIdAndBookingStatus(Long userId, BookingStatus bookingStatus);
}
