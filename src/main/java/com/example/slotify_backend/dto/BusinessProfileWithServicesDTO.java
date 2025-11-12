package com.example.slotify_backend.dto;

import com.example.slotify_backend.entity.ServiceEntity;
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
