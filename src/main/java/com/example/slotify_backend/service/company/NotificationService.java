package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.NotificationAndBusinessImgUrlDTO;
import com.example.slotify_backend.dto.company.NotificationDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.mapper.NotificationMapper;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import com.example.slotify_backend.repository.NotificationRepository;
import com.example.slotify_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JwtService jwtService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final BusinessProfileRepository businessProfileRepository;

    public NotificationAndBusinessImgUrlDTO getAllNotifications(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        String businessImgUrl = businessProfileRepository.findByUserId(userId).getProfilePictureURL();

        return notificationMapper.toEntity(notificationRepository.findAllByUser_Id(userId),businessImgUrl);
    }

    @Transactional
    public void markAsReadedNotification(long notificationId) {
          notificationRepository.findById(notificationId).ifPresent(notification -> {
              notification.setIsReaded(true);
          });
    }

    @Transactional
    public void markAsReadedAllNotification(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        notificationRepository.findAllByUser_Id(userId).forEach(notification -> {
            notification.setIsReaded(true);
        });
    }
}
