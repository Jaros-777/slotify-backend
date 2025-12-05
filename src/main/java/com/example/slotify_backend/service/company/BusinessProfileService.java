package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.BusinessProfileDTO;
import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.mapper.BusinessProfileMapper;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        System.out.println(profile.getName());
        return businessProfileMapper.nameToDTO(profile);
    }

    public List<String> uploadPictures(List<MultipartFile> files,String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        BusinessProfile profile = businessProfileRepository.findByUserId(userId);



        List<String> picturesUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String ext = Optional.ofNullable(file.getOriginalFilename())
                    .orElse("file");
            String path = "uploads/" + UUID.randomUUID() + "-" + ext;
            picturesUrls.add(supabaseStorageService.uploadPicture(file, path));
        }
//        profile.setProfilePictureURL(picturesUrls.getFirst());
//        profile.setBackgroundPictureURL(picturesUrls.get(1));
        
        return picturesUrls;
    }

    public void updateBusinessProfile(BusinessProfileDTO dto) {

        businessProfileRepository.findById(dto.id()).ifPresent(businessProfile -> {
            businessProfileMapper.updateDTO(dto, businessProfile);
            businessProfileRepository.save(businessProfile);
        });
    };
}
