package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.entity.Availability;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AvailabilityMapper {
    private final UserRepository userRepository;

    public AvailabilityDTO toDTO(Availability availability) {
        return new AvailabilityDTO(
                availability.getId(),
                availability.getDayOfWeek(),
                availability.getOpenHour(),
                availability.getCloseHour(),
                availability.getIsClose()
        );
    }

    public Availability toEntity(AvailabilityDTO availabilityDTO, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        return new Availability(
                availabilityDTO.isClose(),
                availabilityDTO.closeHour(),
                availabilityDTO.openHour(),
                availabilityDTO.dayOfWeek(),
                user
        );
    }

    public List<AvailabilityDTO> toDTO(List<Availability> availabilities) {
        return availabilities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void updateAvailability(AvailabilityDTO availabilityDTO, Availability availability) {
        availability.setCloseHour(availabilityDTO.closeHour());
        availability.setOpenHour(availabilityDTO.openHour());
        availability.setDayOfWeek(availabilityDTO.dayOfWeek());
        availability.setIsClose(availabilityDTO.isClose());

    }
}
