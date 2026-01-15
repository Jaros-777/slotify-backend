package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.BusinessProfileDTO;
import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.entity.BusinessAddress;
import com.example.slotify_backend.entity.BusinessProfile;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BusinessProfileMapper {


    public BusinessProfileDTO toDTO(BusinessProfile profile) {
        BusinessAddress address = profile.getBusinessAddress();
        return new BusinessProfileDTO(
                profile.getId(),
                profile.getName(),
                profile.getSlogan(),
                profile.getDescription(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getWebsiteURL(),
                profile.getFacebookURL(),
                profile.getProfilePictureURL(),
                profile.getBackgroundPictureURL(),
                address.getLat(),
                address.getLng(),
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getNote()
        );
    }
    @Transactional
    public void updateBusinessProfileDTO(BusinessProfileDTO dto, BusinessProfile entity) {
        BusinessAddress address = entity.getBusinessAddress();
        if (dto.businessName() != null) entity.setName(dto.businessName());
        if (dto.slogan() != null) entity.setSlogan(dto.slogan());
        if (dto.description() != null) entity.setDescription(dto.description());
        if (dto.email() != null) entity.setEmail(dto.email());
        if (dto.phone() != null) entity.setPhone(dto.phone());
        if (dto.websiteURL() != null) entity.setWebsiteURL(dto.websiteURL());
        if (dto.facebookURL() != null) entity.setFacebookURL(dto.facebookURL());
        if (dto.lat() != null) address.setLat(dto.lat());
        if (dto.lng() != null) address.setLng(dto.lng());
        if (dto.houseNumber() != null) address.setHouseNumber(dto.houseNumber());
        if (dto.street() != null) address.setStreet(dto.street());
        if (dto.city() != null) address.setCity(dto.city());
        if (dto.postalCode() != null) address.setPostalCode(dto.postalCode());
        if (dto.note() != null) address.setNote(dto.note());

    }

    public BusinessProfileNameDTO nameToDTO(BusinessProfile entity) {
        return new BusinessProfileNameDTO(
                entity.getName()
        );
    }
}
