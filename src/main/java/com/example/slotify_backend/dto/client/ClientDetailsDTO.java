package com.example.slotify_backend.dto.client;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Representation of client details response")
public record ClientDetailsDTO(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotNull
        Integer phone,
        String pictureURL
) {
}
