package com.example.slotify_backend.entity;

import com.example.slotify_backend.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
    private Boolean isReaded;

    public Notification(Boolean isReaded, ServiceEntity service, Event event, LocalDateTime date, NotificationType type, Client client, User user) {
        this.isReaded = isReaded;
        this.service = service;
        this.event = event;
        this.date = date;
        this.type = type;
        this.client = client;
        this.user = user;
    }
}
