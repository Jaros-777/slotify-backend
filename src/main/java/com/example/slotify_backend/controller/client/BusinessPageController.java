package com.example.slotify_backend.controller.client;
import com.example.slotify_backend.dto.BusinessProfileWithServicesDTO;
import com.example.slotify_backend.service.BusinessPageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business-page")
@AllArgsConstructor
public class BusinessPageController {

    private final BusinessPageService businessPageService;

    @GetMapping("/{businessName}")
    public BusinessProfileWithServicesDTO getBusinessProfileDetails(@PathVariable String businessName) {
        return businessPageService.getBusinessProfileDetails(businessName);
    }
}
