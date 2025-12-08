package com.example.slotify_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @NotBlank
    private String name;
    @NotNull
    Integer price;
    @NotNull
    Integer duration;
    String description;
    String servicePictureURL;
    @NotNull
    @Column(name = "is_editable")
    Boolean isEditable;
    @OneToMany(cascade = CascadeType.ALL)
    List<Event> events = new ArrayList<>();

    public ServiceEntity(User user, String name, Integer price, Integer duration, String description, Boolean isEditable) {
        this.user = user;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.isEditable = isEditable;
    }
}
