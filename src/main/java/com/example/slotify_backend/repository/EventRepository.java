package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.Vacation;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserIdAndStartDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Event> findAllEventsByServiceEntity_Id(Long serviceId);
    List<Event> findAllByClientId(Long clientId);
    List<Event> findAllByUserIdAndBookingStatusNot(Long userId, BookingStatus bookingStatus);
    List<Event> findAllByVacation(Vacation vacation);
    List<Event> findAllByUserIdAndStartDateBetweenAndBookingStatus(Long userId, LocalDateTime startDate,LocalDateTime endDate, BookingStatus bookingStatus);
    List<Event> findAllByClientIdAndUserId(Long clientId, Long userId);

    Optional<Event> findByReservationToken(String reservationToken);


    @Modifying
    @Query("DELETE FROM Event e WHERE e.bookingStatus = :status AND e.tokenExpiryDate < :date")
    void deleteExpiredEvents(@Param("status") BookingStatus status, @Param("date") LocalDateTime date);

    @Query("""
    SELECT COUNT(e) > 0 FROM Event e 
    WHERE e.user.id = :userId 
    AND :newStart < e.endDate 
    AND :newEnd > e.startDate
""")
    boolean existsOverlappingEventForUser(
            @Param("userId") Long userId,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );
}
