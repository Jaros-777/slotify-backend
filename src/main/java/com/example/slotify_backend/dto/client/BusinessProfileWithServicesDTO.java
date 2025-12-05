package com.example.slotify_backend.dto.client;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.dto.company.BusinessProfileDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BusinessProfileWithServicesDTO {
    private BusinessProfileDTO businessProfileDTO;
    private List<ServiceDTO> servicesDTO;
    private List<AvailabilityDTO> availabilityDTO;

    public BusinessProfileWithServicesDTO(BusinessProfileDTO businessProfileDTO, List<ServiceDTO> servicesDTO, List<AvailabilityDTO> availabilityDTO) {
        this.businessProfileDTO = businessProfileDTO;
        this.servicesDTO = servicesDTO;
        this.availabilityDTO = availabilityDTO;
    }



}
