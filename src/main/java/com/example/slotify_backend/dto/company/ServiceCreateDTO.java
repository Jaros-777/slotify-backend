package com.example.slotify_backend.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceCreateDTO(
       @NotBlank
       String name,
       String description,
       @NotNull
       Integer price,
       @NotNull
       Integer duration
) {
}
