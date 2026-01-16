package com.example.slotify_backend.dto.client;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.dto.company.BusinessProfileWithAddressDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusinessProfileWithServicesDTO {
    private BusinessProfileWithAddressDTO businessProfileDTO;
    private List<ServiceDTO> servicesDTO;
    private List<AvailabilityDTO> availabilityDTO;

    public BusinessProfileWithServicesDTO(BusinessProfileWithAddressDTO businessProfileDTO, List<ServiceDTO> servicesDTO, List<AvailabilityDTO> availabilityDTO) {
        this.businessProfileDTO = businessProfileDTO;
        this.servicesDTO = servicesDTO;
        this.availabilityDTO = availabilityDTO;
    }



}
