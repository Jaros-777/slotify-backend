package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Representation of login request")
public record UserRequestLoginDTO (
        @NotBlank @Email String email,
        @NotBlank String password
){}
