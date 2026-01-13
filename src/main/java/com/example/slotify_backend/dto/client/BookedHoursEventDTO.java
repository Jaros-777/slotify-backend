package com.example.slotify_backend.dto.client;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record BookedHoursEventDTO(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndTime
) {
}
