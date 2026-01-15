package com.example.slotify_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BusinessAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double lat;
    private Double lng;
    private String houseNumber;
    private String street;
    private String city;
    private String postalCode;
    private String note;

    public BusinessAddress(String note, String postalCode, String city, String street, String houseNumber, Double lng, Double lat) {
        this.note = note;
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.lng = lng;
        this.lat = lat;
    }
}
