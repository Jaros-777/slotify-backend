package com.example.slotify_backend.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Representation of order reservation response")
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
