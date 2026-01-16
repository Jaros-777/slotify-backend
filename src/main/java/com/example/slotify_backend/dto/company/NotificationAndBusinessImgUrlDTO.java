package com.example.slotify_backend.dto.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationAndBusinessImgUrlDTO {
    private String BusinessImgUrl;
    private List<NotificationDTO> notificationDTO;

    public NotificationAndBusinessImgUrlDTO(String businessImgUrl, List<NotificationDTO> notificationDTO) {
        BusinessImgUrl = businessImgUrl;
        this.notificationDTO = notificationDTO;
    }
}
