package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.NotificationDTO;
import com.example.slotify_backend.mapper.NotificationMapper;
import com.example.slotify_backend.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JwtService jwtService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public List<NotificationDTO> getAllNotifications(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        return notificationMapper.toEntity(notificationRepository.findAllByUser_Id(userId));
    }

    @Transactional
    public void markAsReadedNotification(long notificationId) {
          notificationRepository.findById(notificationId).ifPresent(notification -> {
              notification.setIsReaded(true);
          });
    }

    @Transactional
    public void markAsReadedAllNotification(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        notificationRepository.findAllByUser_Id(userId).forEach(notification -> {
            notification.setIsReaded(true);
        });
    }
}
