package com.example.slotify_backend.repository;
import com.example.slotify_backend.entity.Notification;
import com.example.slotify_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUser_Id(Long userId);
    List<Notification> findAllByService_Id(Long serviceId);
}
