package com.example.slotify_backend.dto.client;


public record ClientDetailsDTO(
        String name,
        String email,
        Integer phone
) {
}
