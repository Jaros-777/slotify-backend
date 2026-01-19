package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.dto.company.BusinessProfileWithAddressDTO;
import com.example.slotify_backend.service.company.BusinessProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Business Profile", description = "Business profile operations")
@RestController
@RequestMapping("/business-profile")
@AllArgsConstructor
public class BusinessProfileController {

    private final BusinessProfileService businessProfileService;

    @GetMapping
    public BusinessProfileWithAddressDTO getBusinessProfileDetails(@RequestHeader("Authorization") String authHeader){
        return businessProfileService.getBusinessProfileDetails(authHeader);
    };

    @GetMapping("/name")
    public BusinessProfileNameDTO getBusinessName(@RequestHeader("Authorization") String authHeader){
        return businessProfileService.getBusinessName(authHeader);
    };

    @PostMapping("/pictures")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadPictures(@RequestPart(value = "profilePic", required = false)MultipartFile profilePic,@RequestPart(value = "backgroundPic", required = false)MultipartFile backgroundPic,@RequestHeader("Authorization") String authHeader){
        businessProfileService.uploadPictures(profilePic,backgroundPic,authHeader);
    };


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateBusinessProfile(@Valid @RequestBody BusinessProfileWithAddressDTO dto) {
        businessProfileService.updateBusinessProfile(dto);
    }
}
