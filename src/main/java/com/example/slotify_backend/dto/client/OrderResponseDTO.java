package com.example.slotify_backend.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Representation of order reservation response")
public record OrderResponseDTO(
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
        String reservationToken
) {
}
