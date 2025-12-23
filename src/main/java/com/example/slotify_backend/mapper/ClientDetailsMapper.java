package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ClientDetailsMapper {

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
}
