package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.ServiceCreateDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
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

    public ServiceEntity toEntity(ServiceCreateDTO dto, Long userId, Boolean toIsEditable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return  new ServiceEntity(
                user,
                dto.name(),
                dto.price(),
                dto.duration(),
                dto.description(),
                toIsEditable
        );
    }

    public ServiceDTO toDTO(ServiceEntity entity) {
        return new ServiceDTO(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDuration(),
                entity.getDescription(),
                entity.getIsEditable(),
                entity.getServicePictureURL()
        );
    }

    public List<ServiceDTO> toDTO(List<ServiceEntity> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void updateDTO(ServiceDTO dto, ServiceEntity entity, Long userId) {
        if (userId != null) {
            User user =  userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            entity.setUser(user);
        }

        if (dto.name() != null) entity.setName(dto.name());
        if (dto.price() != null) entity.setPrice(dto.price());
        if (dto.duration() != null) entity.setDuration(dto.duration());
        if(dto.description() != null) entity.setDescription(dto.description());
    }
}