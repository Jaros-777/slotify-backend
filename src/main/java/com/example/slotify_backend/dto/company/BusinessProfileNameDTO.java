package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Representation of business profile name")
public record BusinessProfileNameDTO(
        @NotBlank
        String businessName
) {
}
