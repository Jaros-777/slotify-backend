package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestRegisterDTO (
    @NotBlank
     String name,
    @NotBlank
    @Email
    String email,
    String password,
    String businessName,
    @NotNull
    Integer phone,
    @NotNull
    Role role
){}
