package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Representation of user details")
public record UserDTO(
        @NotBlank
        String name,
        @NotBlank
        String email
) {
}
