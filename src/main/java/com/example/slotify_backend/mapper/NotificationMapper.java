package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.NotificationAndBusinessImgUrlDTO;
import com.example.slotify_backend.dto.company.NotificationDTO;
import com.example.slotify_backend.entity.Notification;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.NotificationType;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationMapper {

    public NotificationAndBusinessImgUrlDTO toEntity(List<Notification> notifications, String businessImgUrl) {
        List<NotificationDTO> dtos = new ArrayList<>();


        notifications.forEach(notification ->

            dtos.add(new NotificationDTO(
                    notification.getId(),
                    notification.getClient().getName(),
                    notification.getClient().getUserAccount() != null
                            ? notification.getClient().getUserAccount().getPictureURL()
                            : null,
                    NotificationType.BOOKING,
                    notification.getDate(),
                    notification.getEvent().getStartDate(),
                    notification.getService().getName(),
                    notification.getIsReaded()
            ))
        );

        return new NotificationAndBusinessImgUrlDTO(
                businessImgUrl,
                dtos
        );
    }

}
