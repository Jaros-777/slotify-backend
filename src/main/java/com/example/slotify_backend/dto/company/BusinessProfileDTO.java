package com.example.slotify_backend.dto.company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BusinessProfileDTO(
        @NotNull
        Long id,
        @NotNull
        Long userId,
        @NotBlank
        String businessName,
        String slogan,
        String description,
        @Email
        String email,
        Integer phone,
        String websiteURL,
        String facebookURL
        ) {
}
