package com.example.slotify_backend.dto.company;

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
