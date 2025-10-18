package com.example.slotify_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name ="services")
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @NotBlank
    private String name;
    @NotBlank
    Integer price;
    @NotBlank
    Integer duration;
    @OneToMany
    List<Event> events = new ArrayList<>();

    public ServiceEntity(User owner, String name, Integer price, Integer duration) {
        this.owner = owner;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }
}
