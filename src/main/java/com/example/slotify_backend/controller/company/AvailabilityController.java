package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.service.company.AvailabilityService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
@AllArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping
    public List<AvailabilityDTO> getAvailabilty(@RequestHeader("Authorization") String authHeader) {
        return availabilityService.getAvailabilty(authHeader);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAvailabilty(@Valid @RequestBody List<AvailabilityDTO> availabilityDTO) {
        availabilityService.updateAvailabilty( availabilityDTO);
    }
}
