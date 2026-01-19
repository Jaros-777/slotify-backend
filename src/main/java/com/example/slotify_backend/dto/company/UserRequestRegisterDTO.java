package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Representation of register request")
public record UserRequestRegisterDTO (
    @NotBlank
     String name,
    @NotBlank
    @Email
    String email,
    String password,
    String businessName,
    Integer phone,
    @NotNull
    Role role
){}
