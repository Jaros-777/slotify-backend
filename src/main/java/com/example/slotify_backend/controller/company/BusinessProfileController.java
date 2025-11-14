package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.BusinessProfileDTO;
import com.example.slotify_backend.dto.company.BusinessProfileNameDTO;
import com.example.slotify_backend.service.company.BusinessProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business-profile")
@AllArgsConstructor
public class BusinessProfileController {

    private final BusinessProfileService businessProfileService;

    @GetMapping
    public BusinessProfileDTO getBusinessProfileDetails(@RequestHeader("Authorization") String authHeader){
        return businessProfileService.getBusinessProfileDetails(authHeader);
    };

    @GetMapping("/name")
    public BusinessProfileNameDTO getBusinessName(@RequestHeader("Authorization") String authHeader){
        return businessProfileService.getBusinessName(authHeader);
    };


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateBusinessProfile(@Valid @RequestBody BusinessProfileDTO dto) {
        businessProfileService.updateBusinessProfile(dto);
    }
}
