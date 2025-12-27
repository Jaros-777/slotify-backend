package com.example.slotify_backend.dto.client;

import java.time.LocalDateTime;

public record ClientBookingsDTO(
        Long eventId,
        LocalDateTime date,
        String businessName,
        String businessProfilePicURL,
        String serviceName,
        Integer servicePrice
) {
}
