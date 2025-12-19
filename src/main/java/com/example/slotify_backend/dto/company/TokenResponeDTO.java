package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.Role;

public record TokenResponeDTO(
        String token,
        Role role
) {
}
