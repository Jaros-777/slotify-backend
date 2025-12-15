package com.example.slotify_backend.dto.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record OrderResponseDTO(
        @NotNull
        Long serviceId,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime chosenDate,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String email,
        @NotNull
        Integer phone,
        String description,
        @NotNull
        Boolean agreements,
        @NotNull
        Boolean loggedClient
) {
}
