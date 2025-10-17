package com.example.slotify_backend.dto;

import jakarta.validation.constraints.NotNull;

public record EventOwnerIdDTO(
        @NotNull
        Long ownerId
) {
}
