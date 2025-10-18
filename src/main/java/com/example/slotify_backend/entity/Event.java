package com.example.slotify_backend.entity;

import com.example.slotify_backend.entity.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @NotBlank
    private String name;
    @Email
    private String email;
    private Integer phone;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private String description;

    public Event(User owner, String name, String email, Integer phone, Service service, LocalDateTime startDate, LocalDateTime endDate, BookingStatus bookingStatus, String description) {
        this.owner = owner;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.service = service;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
        this.description = description;
    }
}


