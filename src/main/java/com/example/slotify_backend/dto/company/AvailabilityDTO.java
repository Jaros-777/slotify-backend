package com.example.slotify_backend.dto.company;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Representation of user availability")
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
