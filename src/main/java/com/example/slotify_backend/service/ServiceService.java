package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.ServiceCreateDTO;
import com.example.slotify_backend.dto.ServiceDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.mapper.ServiceMapper;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import com.example.slotify_backend.entity.ServiceEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final JwtService jwtService;
    private final EventRepository eventRepository;

    public List<ServiceDTO> getAllServicesByUser(String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        List<ServiceEntity> services =serviceRepository.findAllByUserId(userId);
        return serviceMapper.toDTO(services);
    }

    public void createNewService(ServiceCreateDTO dto,String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        serviceRepository.save(serviceMapper.toEntity(dto, userId, true));
    }

    @Transactional
    public void deleteServiceById(Long serviceId, String authHeader) {
        List<Event> eventsList = eventRepository.findAllEventsByServiceEntity_Id(serviceId);


        if(!eventsList.isEmpty()){
            String token = authHeader.replace("Bearer ", "").trim();
            Long userId = jwtService.getUserIdFromToken(token);

            ServiceEntity defaultService = serviceRepository.findByIsEditableAndUserId(false,userId);

            for (Event event : eventsList) {
                event.setServiceEntity(defaultService);
            }
            eventRepository.saveAll(eventsList);
        }

        serviceRepository.deleteById(serviceId);
    }

    public void updateServiceById(ServiceDTO dto,String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        serviceRepository.findById(dto.id()).ifPresent(serviceEntity -> {
            serviceMapper.updateDTO(dto, serviceEntity,userId);
            serviceRepository.save(serviceEntity);
        });
    };
}
