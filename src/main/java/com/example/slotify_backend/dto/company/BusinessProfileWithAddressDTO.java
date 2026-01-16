package com.example.slotify_backend.dto.company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessProfileWithAddressDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String businessName;
    private String slogan;
    private String description;
    @Email
    private String email;
    private Integer phone;
    private String websiteURL;
    private String facebookURL;
    private String profilePictureURL;
    private String backgroundPictureURL;
    private BusinessAddressDTO address;

    public BusinessProfileWithAddressDTO(Long id, String businessName, String slogan, String description, String email, Integer phone, String websiteURL, String facebookURL, String profilePictureURL, String backgroundPictureURL, BusinessAddressDTO address) {
        this.id = id;
        this.businessName = businessName;
        this.slogan = slogan;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.websiteURL = websiteURL;
        this.facebookURL = facebookURL;
        this.profilePictureURL = profilePictureURL;
        this.backgroundPictureURL = backgroundPictureURL;
        this.address = address;
    }
}
