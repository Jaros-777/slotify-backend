package com.example.slotify_backend.entity;

import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "events")
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = true)
    Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = true)
    private ServiceEntity serviceEntity;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    public Event(User user, Client client, ServiceEntity serviceEntity, LocalDateTime startDate, LocalDateTime endDate, BookingStatus bookingStatus, String description) {
        this.user = user;
        this.client = client;
        this.serviceEntity = serviceEntity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
        this.description = description;
    }

    public Event(User user, LocalDateTime startDate, LocalDateTime endDate, String description, BookingStatus bookingStatus, Vacation vacation) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.bookingStatus = bookingStatus;
        this.vacation = vacation;
    }
}


