package com.example.slotify_backend.service.company;
import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.entity.Availability;
import com.example.slotify_backend.mapper.AvailabilityMapper;
import com.example.slotify_backend.repository.AvailabiltyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailabilityService {
    private final AvailabiltyRepository availabiltyRepository;
    private final JwtService jwtService;
    private final AvailabilityMapper availabilityMapper;

    public List<AvailabilityDTO> getAvailability(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        List<AvailabilityDTO> list = new ArrayList<>();

        availabiltyRepository.findAllByUserId(userId).forEach(availability ->
            list.add(availabilityMapper.toDTO(availability))
        );
        return list;

    }

    @Transactional
    public void updateAvailability( List<AvailabilityDTO> availabilityDTO) {

        for (AvailabilityDTO currentAvailability : availabilityDTO) {
            Availability current = availabiltyRepository.findById(currentAvailability.id());
            availabilityMapper.updateAvailability(currentAvailability, current);
        }

    }
}
