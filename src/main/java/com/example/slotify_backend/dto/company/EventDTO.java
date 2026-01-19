package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Representation event response")
public record EventDTO(
        @NotNull
        Long id,
        @NotBlank
        String clientName,
        @Email
        String clientEmail,
        Integer clientPhone,
        Long serviceId,
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

