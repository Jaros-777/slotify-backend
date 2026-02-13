package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.dto.client.BusinessProfileWithServicesDTO;
import com.example.slotify_backend.dto.company.BusinessProfileWithAddressDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.mapper.AvailabilityMapper;
import com.example.slotify_backend.mapper.BusinessProfileMapper;
import com.example.slotify_backend.mapper.ServiceMapper;
import com.example.slotify_backend.repository.AvailabiltyRepository;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BusinessPageService {

    private BusinessProfileRepository businessProfileRepository;
    private BusinessProfileMapper businessProfileMapper;
    private ServiceRepository serviceRepository;
    private AvailabilityMapper availabilityMapper;
    private AvailabiltyRepository availabiltyRepository;
    private ServiceMapper serviceMapper;

    public BusinessProfileWithServicesDTO getBusinessProfileDetails(String businessName) {
         BusinessProfile businessProfile = businessProfileRepository.findByNameIgnoreCase(businessName).orElseThrow(()-> new EntityNotFoundException(businessName));

         BusinessProfileWithAddressDTO businessProfileDTO = businessProfileMapper.toDTO(businessProfile);

         List<ServiceEntity> services = serviceRepository.findAllByUserId(businessProfile.getUser().getId());
         List<ServiceDTO> serviceDto = serviceMapper.toDTO(services);
         List<AvailabilityDTO> availabilityDTO =availabilityMapper.toDTO(availabiltyRepository.findAllByUserId(businessProfile.getUser().getId()));

         return new BusinessProfileWithServicesDTO(businessProfileDTO, serviceDto, availabilityDTO);

    }
}
