package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.NotificationDTO;
import com.example.slotify_backend.service.company.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getAllNotifications(@RequestHeader("Authorization") String authHeader) {
        return notificationService.getAllNotifications(authHeader);
    }

    @PostMapping("/{notificationId}")
    public void markAsReadedNotification(@PathVariable Long notificationId) {
        notificationService.markAsReadedNotification(notificationId);
    }

    @PostMapping("/mark-all")
    @ResponseStatus(HttpStatus.OK)
    public void markAsReadedAllNotification(@RequestHeader("Authorization") String authHeader) {
        notificationService.markAsReadedAllNotification(authHeader);
    }
}
