package com.example.slotify_backend.dto.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PreOrderResponseDTO(
        @NotNull
        Long userId,
        @NotNull
        Long serviceId,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime chosenDate
) {
}
