package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.BusinessProfileDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BusinessProfileMapper {


    public BusinessProfileDTO toDTO(BusinessProfile profile) {
        return new BusinessProfileDTO(
                profile.getId(),
                profile.getUser().getId(),
                profile.getName(),
                profile.getSlogan(),
                profile.getDescription(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getWebsiteURL(),
                profile.getFacebookURL()
        );
    }

    public void updateDTO(BusinessProfileDTO dto, BusinessProfile entity) {
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.slogan() != null) entity.setSlogan(dto.slogan());
        if (dto.description() != null) entity.setDescription(dto.description());
        if(dto.email() != null) entity.setEmail(dto.email());
        if(dto.phone() != null) entity.setPhone(dto.phone());
        if (dto.websiteURL() != null) entity.setWebsiteURL(dto.websiteURL());
        if (dto.facebookURL() != null) entity.setFacebookURL(dto.facebookURL());
    }
}
