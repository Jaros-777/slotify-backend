package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Representation of event request")
public record EventCreateDTO(
        Long clientId,
        @NotNull
        Long serviceId,
        @NotBlank
        String clientName,
        @NotBlank
        String clientEmail,
        Integer clientPhone,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        @Enumerated(EnumType.STRING)
        BookingStatus bookingStatus,
        String description
) {
}

