package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "Representation of notification and business profile picture response")
@Getter
@Setter
public class NotificationAndBusinessImgUrlDTO {
    private String businessImgUrl;
    private List<NotificationDTO> notificationDTO;

    public NotificationAndBusinessImgUrlDTO(String businessImgUrl, List<NotificationDTO> notificationDTO) {
        this.businessImgUrl = businessImgUrl;
        this.notificationDTO = notificationDTO;
    }
}
