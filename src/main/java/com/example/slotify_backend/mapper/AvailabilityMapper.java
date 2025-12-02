package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.entity.Availability;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityMapper {

    public AvailabilityDTO toDTO(Availability availability) {
        return new AvailabilityDTO(
                availability.getId(),
                availability.getDayOfWeek(),
                availability.getOpenHour(),
                availability.getCloseHour(),
                availability.getIsClose()
        );
    }
}
