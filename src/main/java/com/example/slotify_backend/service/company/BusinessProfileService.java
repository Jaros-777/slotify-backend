package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.BusinessProfileDTO;
import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.mapper.BusinessProfileMapper;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BusinessProfileService {

    private final BusinessProfileRepository businessProfileRepository;
    private final JwtService jwtService;
    private final BusinessProfileMapper businessProfileMapper;
    private final SupabaseStorageService supabaseStorageService;

    public BusinessProfileDTO getBusinessProfileDetails(String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        BusinessProfile profile = businessProfileRepository.findByUserId(userId);
        return businessProfileMapper.toDTO(profile);
    }

    public BusinessProfileNameDTO getBusinessName(String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        BusinessProfile profile = businessProfileRepository.findByUserId(userId);
        return businessProfileMapper.nameToDTO(profile);
    }

    @Transactional
    public void uploadPictures(MultipartFile profilePic,MultipartFile backgroundPic,String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        BusinessProfile profile = businessProfileRepository.findByUserId(userId);

//        if(profile.getProfilePictureURL() != null && profilePic != null){
//            supabaseStorageService.deletePicture(profile.getProfilePictureURL());
//            System.out.println("DELETED PROFILE PICTURE");
//        }
//        if(profile.getBackgroundPictureURL() != null && backgroundPic != null){
//            System.out.println("-------- " + profile.getBackgroundPictureURL());
//            supabaseStorageService.deletePicture(profile.getBackgroundPictureURL());
//            System.out.println("DELETED PROFILE BACKGROUND PICTURE");
//        }

        if(profilePic != null){
            profile.setProfilePictureURL(supabaseStorageService.uploadPicture(profilePic, "/"+ userId + UUID.randomUUID() +"profilePic"));
        }
        if(backgroundPic != null){
            System.out.println("SENDED BACKGROUND PICTURE");
            profile.setBackgroundPictureURL(supabaseStorageService.uploadPicture(backgroundPic, "/"+ userId + UUID.randomUUID() + "backgroundPic"));
        }

        System.out.println("UPLOADED PICTURES");
    }

    public void updateBusinessProfile(BusinessProfileDTO dto) {

        businessProfileRepository.findById(dto.id()).ifPresent(businessProfile -> {
            businessProfileMapper.updateDTO(dto, businessProfile);
            businessProfileRepository.save(businessProfile);
        });
    };
}
