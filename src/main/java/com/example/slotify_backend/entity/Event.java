package com.example.slotify_backend.entity;

import com.example.slotify_backend.entity.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private String description;

}


