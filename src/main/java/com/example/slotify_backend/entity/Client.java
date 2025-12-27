package com.example.slotify_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
@NoArgsConstructor
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true)
    private String email;
    private Integer phone;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User userAccount;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Client(String name, String email, Integer phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
