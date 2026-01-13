package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.NotificationDTO;
import com.example.slotify_backend.entity.Notification;
import com.example.slotify_backend.entity.enums.NotificationType;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationMapper {

    public List<NotificationDTO> toEntity(List<Notification> notifications) {
        List<NotificationDTO> dtos = new ArrayList<>();

        notifications.forEach(notification -> {
            dtos.add(new NotificationDTO(
                    notification.getId(),
                    notification.getClient().getName(),
                    notification.getClient().getUserAccount().getPictureURL(),
                    NotificationType.BOOKING,
                    notification.getDate(),
                    notification.getEvent().getStartDate(),
                    notification.getService().getName(),
                    notification.getIsReaded()
            ));
        });
        return dtos;
    };

}
