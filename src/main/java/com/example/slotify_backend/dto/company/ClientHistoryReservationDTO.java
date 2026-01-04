package com.example.slotify_backend.dto.company;

import java.time.LocalDateTime;

public record ClientHistoryReservationDTO(
        Long id,
        Long clientId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String serviceName
) {
}
