package com.example.slotify_backend.dto.company;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Representation of user services")
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
