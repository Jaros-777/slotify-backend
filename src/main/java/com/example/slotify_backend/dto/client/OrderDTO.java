package com.example.slotify_backend.dto.client;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private final List<AvailabilityDTO> availabiltyDTO;
    private final ServiceDTO serviceDTO;
    private final String bussinessName;
    private String bussinessPictureUrl;

    public OrderDTO(List<AvailabilityDTO> availabiltyDTO, ServiceDTO serviceDTO, String bussinessName, String bussinessPictureUrl) {
        this.availabiltyDTO = availabiltyDTO;
        this.serviceDTO = serviceDTO;
        this.bussinessName = bussinessName;
        this.bussinessPictureUrl = bussinessPictureUrl;
    }
}
