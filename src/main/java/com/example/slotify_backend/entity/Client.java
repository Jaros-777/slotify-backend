package com.example.slotify_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    private Long id;
    @NotBlank
    private String name;
    private String email;
    private Integer phone;
    @OneToMany
    private List<Event> events = new ArrayList<>();
}
