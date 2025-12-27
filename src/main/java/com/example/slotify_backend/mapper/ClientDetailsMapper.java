package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.client.ClientBookingsDTO;
import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientDetailsMapper {

    private final UserRepository userRepository;

    public ClientDetailsMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClientDetailsDTO toDTO(User user) {
        return new ClientDetailsDTO(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getPictureURL()
        );
    }

    public void updateDTO(ClientDetailsDTO dto, User user) {
        if (dto.name() != null) user.setName(dto.name());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.phone() != null) user.setPhone(dto.phone());
    }

    public ClientBookingsDTO toClientBookingDTO(Event event) {
        System.out.println(event.getUser().getId());
        System.out.println(event.getUser().getId());
        User businessUser = userRepository.findById(event.getUser().getId()).orElse(null);
        return new ClientBookingsDTO(
                event.getId(),
                event.getStartDate(),
                businessUser.getBusinessProfile().getName(),
                businessUser.getBusinessProfile().getProfilePictureURL(),
                event.getServiceEntity().getName(),
                event.getServiceEntity().getPrice()
        );
    }
}
