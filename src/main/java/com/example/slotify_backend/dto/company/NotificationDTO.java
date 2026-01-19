package com.example.slotify_backend.dto.company;

import com.example.slotify_backend.entity.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Representation of notification response")
public record NotificationDTO(
        @NotNull
        Long id,
        @NotBlank
        String clientName,
        @NotBlank
        String clientImgUrl,
        @NotBlank
        NotificationType type,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime bookingStartDate,
        @NotBlank
        String serviceName,
        @NotNull
        Boolean isReaded
) {
}
