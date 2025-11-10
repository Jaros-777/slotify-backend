package com.example.slotify_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "business_profile")
@Getter
@Setter
@NoArgsConstructor
public class BusinessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @NotBlank
    @Column(unique = true)
    private String name;
    private String slogan;
    private String description;
    @Email
    private String email;
    private Integer phone;
    private String websiteURL;
    private String facebookURL;

    public BusinessProfile(User user ,String name) {
        this.user = user;
        this.name = name;
    }
}
