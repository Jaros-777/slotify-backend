package com.example.slotify_backend.dto.company;

import jakarta.validation.constraints.NotBlank;

public record BusinessProfileNameDTO(
        @NotBlank
        String businessName
) {
}
