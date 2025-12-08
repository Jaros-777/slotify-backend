package com.example.slotify_backend.dto.company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceDTO(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotNull
        Integer price,
        @NotNull
        Integer duration,
        String description,
        Boolean isEditable,
        String servicePictureURL
) {
}
