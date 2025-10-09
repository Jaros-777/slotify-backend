package com.example.slotify_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestLoginDTO (
        @NotBlank @Email String email,
        @NotBlank String password
){}
