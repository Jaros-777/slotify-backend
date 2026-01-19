package com.example.slotify_backend.controller.client;
import com.example.slotify_backend.dto.client.BusinessProfileWithServicesDTO;
import com.example.slotify_backend.service.client.BusinessPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Business Page", description = "Business page details operation")
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
