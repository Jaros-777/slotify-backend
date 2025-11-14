package com.example.slotify_backend.dto.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestRegisterDTO (
    @NotBlank
     String name,
    @NotBlank
    @Email
    String email,
    @NotBlank
     String password,
    @NotBlank
    String businessName

){}
