package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Representation of client history reservation response")
public record ClientHistoryReservationDTO(
        Long id,
        Long clientId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String serviceName
) {
}
