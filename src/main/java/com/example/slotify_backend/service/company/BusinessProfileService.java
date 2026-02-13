package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.dto.company.BusinessProfileWithAddressDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.mapper.BusinessProfileMapper;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BusinessProfileService {

    private final BusinessProfileRepository businessProfileRepository;
    private final JwtService jwtService;
    private final BusinessProfileMapper businessProfileMapper;
    private final SupabaseStorageService supabaseStorageService;

    public BusinessProfileWithAddressDTO getBusinessProfileDetails(String authHeader){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        BusinessProfile profile = businessProfileRepository.findByUserId(userId);
        return businessProfileMapper.toDTO(profile);
    }

    public BusinessProfileNameDTO getBusinessName(String authHeader){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        BusinessProfile profile = businessProfileRepository.findByUserId(userId);
        return businessProfileMapper.nameToDTO(profile);
    }

    @Transactional
    public void uploadPictures(MultipartFile profilePic,MultipartFile backgroundPic,String authHeader){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        BusinessProfile profile = businessProfileRepository.findByUserId(userId);

        if(profile.getProfilePictureURL() != null && profilePic != null){
            supabaseStorageService.deletePictureByPublicUrl(profile.getProfilePictureURL());
        }
        if(profile.getBackgroundPictureURL() != null && backgroundPic != null){
            supabaseStorageService.deletePictureByPublicUrl(profile.getBackgroundPictureURL());
        }

        if(profilePic != null){
            profile.setProfilePictureURL(supabaseStorageService.uploadPicture(profilePic, "/"+ userId + UUID.randomUUID() +"profilePic"));
        }
        if(backgroundPic != null){
            profile.setBackgroundPictureURL(supabaseStorageService.uploadPicture(backgroundPic, "/"+ userId + UUID.randomUUID() + "backgroundPic"));
        }
    }

    public void updateBusinessProfile(BusinessProfileWithAddressDTO dto) {
        businessProfileRepository.findById(dto.getId()).ifPresent(businessProfile -> {
            businessProfileMapper.updateBusinessProfileDTO(dto, businessProfile);
        });
    };
}
