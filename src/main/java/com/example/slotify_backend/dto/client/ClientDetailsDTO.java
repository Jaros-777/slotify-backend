package com.example.slotify_backend.dto.client;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientDetailsDTO(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotNull
        Integer phone,
        String pictureURL
) {
}
