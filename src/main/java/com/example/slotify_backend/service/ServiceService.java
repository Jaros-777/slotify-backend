package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.ServiceCreateDTO;
import com.example.slotify_backend.dto.ServiceDTO;
import com.example.slotify_backend.mapper.ServiceMapper;
import com.example.slotify_backend.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import com.example.slotify_backend.entity.ServiceEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public List<ServiceDTO> getAllServicesByUser(Long userId){
        List<ServiceEntity> services =serviceRepository.findAllByUserId(userId);
        return serviceMapper.toDTO(services);
    }

    public void createNewService(ServiceCreateDTO dto) {
        serviceRepository.save(serviceMapper.toEntity(dto));
    }

    public void deleteServiceById(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    public void updateServiceById(ServiceDTO dto) {
        serviceRepository.findById(dto.id()).ifPresent(serviceEntity -> {
            serviceMapper.updateDTO(dto, serviceEntity);
            serviceRepository.save(serviceEntity);
        });
    };
}
