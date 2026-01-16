package com.example.slotify_backend.dto.company;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String name,
        @NotBlank
        String email
) {
}
