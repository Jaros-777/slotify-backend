package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.BusinessAddressDTO;
import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.dto.company.BusinessProfileWithAddressDTO;
import com.example.slotify_backend.entity.BusinessAddress;
import com.example.slotify_backend.entity.BusinessProfile;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BusinessProfileMapper {


    public BusinessProfileWithAddressDTO toDTO(BusinessProfile profile) {
        BusinessAddressDTO address = addressToDTO(profile.getBusinessAddress());
        return new BusinessProfileWithAddressDTO(
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
                address

        );
    }
    @Transactional
    public void updateBusinessProfileDTO(BusinessProfileWithAddressDTO dto, BusinessProfile entity) {
        BusinessAddressDTO addressDTO = dto.getAddress();
        BusinessAddress address = entity.getBusinessAddress();
        if (dto.getBusinessName() != null) entity.setName(dto.getBusinessName());
        if (dto.getSlogan() != null) entity.setSlogan(dto.getSlogan());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getWebsiteURL() != null) entity.setWebsiteURL(dto.getWebsiteURL());
        if (dto.getFacebookURL() != null) entity.setFacebookURL(dto.getFacebookURL());
        if (addressDTO.lat() != null) address.setLat(addressDTO.lat());
        if (addressDTO.lng() != null) address.setLng(addressDTO.lng());
        if (addressDTO.houseNumber() != null) address.setHouseNumber(addressDTO.houseNumber());
        if (addressDTO.street() != null) address.setStreet(addressDTO.street());
        if (addressDTO.city() != null) address.setCity(addressDTO.city());
        if (addressDTO.postalCode() != null) address.setPostalCode(addressDTO.postalCode());
        if (addressDTO.note() != null) address.setNote(addressDTO.note());

    }

    public BusinessProfileNameDTO nameToDTO(BusinessProfile entity) {
        return new BusinessProfileNameDTO(
                entity.getName()
        );
    }

    private BusinessAddressDTO addressToDTO(BusinessAddress address) {
        return new BusinessAddressDTO(
                address.getLat(),
                address.getLng(),
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getNote()
        );
    }
}
