package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representation of token validation")
public record TokenResponeDTO(
        String token,
        Role role
) {
}
