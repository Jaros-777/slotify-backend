package com.example.slotify_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @NotNull
    private Integer dayOfWeek;
    @NotBlank
    private String openHour;
    @NotBlank
    private String closeHour;
    @NotNull
    private Boolean isClose;

    public Availability(Boolean isClose, String closeHour, String openHour, Integer dayOfWeek, User user) {
        this.isClose = isClose;
        this.closeHour = closeHour;
        this.openHour = openHour;
        this.dayOfWeek = dayOfWeek;
        this.user = user;
    }


}
