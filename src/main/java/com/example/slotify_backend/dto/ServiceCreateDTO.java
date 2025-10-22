package com.example.slotify_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceCreateDTO(
        @NotNull
       Long userId,
       @NotBlank
       String name,
       @NotNull
       Integer price,
       @NotNull
       Integer duration
) {
}
