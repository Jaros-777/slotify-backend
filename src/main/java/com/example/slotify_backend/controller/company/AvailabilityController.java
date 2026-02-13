package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.service.company.AvailabilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Availability", description = "Availability operations")
@RestController
@RequestMapping("/availability")
@AllArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping
    public List<AvailabilityDTO> getAvailability(@RequestHeader("Authorization") String authHeader) {
        return availabilityService.getAvailability(authHeader);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAvailability(@Valid @RequestBody List<AvailabilityDTO> availabilityDTO) {
        availabilityService.updateAvailability( availabilityDTO);
    }
}
