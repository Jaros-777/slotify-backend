package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Representation of creating user service")
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
