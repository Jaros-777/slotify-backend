package com.example.slotify_backend.service.company;
import com.example.slotify_backend.dto.company.AvailabilityDTO;
import com.example.slotify_backend.mapper.AvailabilityMapper;
import com.example.slotify_backend.repository.AvailabiltyRepository;
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

    public List<AvailabilityDTO> getAvailabilty(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        List<AvailabilityDTO> list = new ArrayList<>();

        availabiltyRepository.findAllByUserId(userId).forEach(availabilty -> {
            list.add(availabilityMapper.toDTO(availabilty));
        });
        return list;

    }

    public void updateAvailabilty(String authHeader, AvailabilityDTO availabilityDTO) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
    }
}
