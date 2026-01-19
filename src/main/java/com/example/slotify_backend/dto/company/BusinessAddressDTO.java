package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representation of business profile address")
public record BusinessAddressDTO(
        Double lat,
        Double lng,
        String houseNumber,
        String street,
        String city,
        String postalCode,
        String note
) {
}
