package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.ServiceCreateDTO;
import com.example.slotify_backend.dto.ServiceDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ServiceMapper {

    private final UserRepository userRepository;

    public ServiceEntity toEntity(ServiceCreateDTO dto) {
        User user = userRepository.findById(dto.ownerId()).orElse(null);
        return  new ServiceEntity(
                user,
                dto.name(),
                dto.price(),
                dto.duration()
        );
    };

    public ServiceDTO toDTO(ServiceEntity entity) {
        return new ServiceDTO(
                entity.getId(),
                entity.getOwner().getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDuration()
        );
    }

    public List<ServiceDTO> toDTO(List<ServiceEntity> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void updateDTO(ServiceDTO dto, ServiceEntity entity) {
        if (dto.userId() != null) {
            User user =  userRepository.findById(dto.userId()).orElse(null);
            entity.setOwner(user);
        }

        if (dto.name() != null) entity.setName(dto.name());
        if (dto.price() != null) entity.setPrice(dto.price());
        if (dto.duration() != null) entity.setDuration(dto.duration());
    }
}