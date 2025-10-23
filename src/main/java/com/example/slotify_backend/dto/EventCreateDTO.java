package com.example.slotify_backend.dto;

import com.example.slotify_backend.entity.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EventCreateDTO(
        Long clientId,
        @NotNull
        Long serviceId,
        @NotBlank
        String clientName,
        @NotBlank
        String email,
        Integer phone,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startDate,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime endDate,
        @NotNull
        @Enumerated(EnumType.STRING)
        BookingStatus bookingStatus,
        String description
) {
}

