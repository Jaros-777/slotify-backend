package com.example.slotify_backend.dto;

import com.example.slotify_backend.entity.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventCreateDTO(
        @NotNull
        Long ownerId,
        @NotBlank
        String name,
        @Email
        String email,
        Integer phone,
        Long serviceId,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        @Enumerated(EnumType.STRING)
        BookingStatus bookingStatus,
        String description
) {}

