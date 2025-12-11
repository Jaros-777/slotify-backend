package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.entity.Availability;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.repository.AvailabiltyRepository;
import com.example.slotify_backend.repository.BusinessProfileRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import com.example.slotify_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final AvailabiltyRepository availabiltyRepository;
    private final ServiceRepository serviceRepository;
    private final AvailabilityMapper availabilityMapper;
    private final ServiceMapper serviceMapper;
    private final BusinessProfileRepository businessProfileRepository;


    public OrderDTO toDTO(Long serviceId) {
        ServiceEntity service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        Long userId = service.getUser().getId();
        BusinessProfile profile = businessProfileRepository.findByUserId(userId);

        String businessPictureUrl = null;
        if (profile != null) {
            businessPictureUrl = profile.getProfilePictureURL();
        }
        List<Availability> availability = availabiltyRepository.findAllByUserId(userId);

        return new OrderDTO(
                availabilityMapper.toDTO(availability),
                serviceMapper.toDTO(service),
                profile.getName(),
                businessPictureUrl
        );
    }
}
