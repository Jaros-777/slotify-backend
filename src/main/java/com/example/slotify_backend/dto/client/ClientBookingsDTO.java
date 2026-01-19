package com.example.slotify_backend.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Representation of client booking history")
public record ClientBookingsDTO(
        Long eventId,
        LocalDateTime date,
        String businessName,
        String businessProfilePicURL,
        String serviceName,
        Integer servicePrice
) {
}
