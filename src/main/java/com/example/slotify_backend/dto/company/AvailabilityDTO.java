package com.example.slotify_backend.dto.company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvailabilityDTO(
        @NotNull
        Long id,
        @NotNull
        Integer dayOfWeek,
        @NotBlank
        String openHour,
        @NotBlank
        String closeHour,
        @NotNull
        Boolean isClose
) {
}
