package com.example.slotify_backend.service.company;

import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventsCleanupService {
    private final EventRepository eventRepository;

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void cleanupExpiredEvents() {
        eventRepository.deleteExpiredEvents(BookingStatus.PENDING, LocalDateTime.now());
    }
}
