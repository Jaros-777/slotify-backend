package com.example.slotify_backend.dto.client;

import com.example.slotify_backend.dto.company.BusinessProfileDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusinessProfileWithServicesDTO {
    private BusinessProfileDTO businessProfileDTO;
    private List<ServiceDTO> servicesDTO;

    public BusinessProfileWithServicesDTO(BusinessProfileDTO businessProfileDTO, List<ServiceDTO> servicesDTO) {
        this.businessProfileDTO = businessProfileDTO;
        this.servicesDTO = servicesDTO;
    }



}
