package com.example.slotify_backend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record AllUserEventsInWeekDTO(
        @NotNull
        Long ownerId,
        @NotNull
        LocalDateTime startTime
) {
}
