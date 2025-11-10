package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.BusinessProfileDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.mapper.BusinessProfileMapper;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusinessProfileService {

    private final BusinessProfileRepository businessProfileRepository;
    private final JwtService jwtService;
    private final BusinessProfileMapper businessProfileMapper;

    public BusinessProfileDTO getBusinessProfileDetails(String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        BusinessProfile profile = businessProfileRepository.findByUserId(userId);
        return businessProfileMapper.toDTO(profile);
    }

    public void updateBusinessProfile(BusinessProfileDTO dto) {

        businessProfileRepository.findById(dto.id()).ifPresent(businessProfile -> {
            businessProfileMapper.updateDTO(dto, businessProfile);
            businessProfileRepository.save(businessProfile);
        });
    };
}
