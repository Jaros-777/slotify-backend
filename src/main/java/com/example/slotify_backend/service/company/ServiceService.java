package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.ServiceCreateDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.mapper.ServiceMapper;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import com.example.slotify_backend.entity.ServiceEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final JwtService jwtService;
    private final EventRepository eventRepository;
    private final SupabaseStorageService storageService;

    public List<ServiceDTO> getAllServicesByUser(String authHeader) {

        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        List<ServiceEntity> services = serviceRepository.findAllByUserId(userId);
        return serviceMapper.toDTO(services);
    }

    public void createNewService(ServiceCreateDTO dto, String authHeader ) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        serviceRepository.save(serviceMapper.toEntity(dto, userId, true));
    }

    @Transactional
    public void deleteServiceById(Long serviceId, String authHeader) {
        List<Event> eventsList = eventRepository.findAllEventsByServiceEntity_Id(serviceId);


        if (!eventsList.isEmpty()) {
            String token = authHeader.replace("Bearer ", "").trim();
            Long userId = jwtService.getUserIdFromToken(token);

            ServiceEntity defaultService = serviceRepository.findByIsEditableAndUserId(false, userId);

            for (Event event : eventsList) {
                event.setServiceEntity(defaultService);
            }
            eventRepository.saveAll(eventsList);
        }

        serviceRepository.deleteById(serviceId);
    }

    public void updateServiceById(ServiceDTO dto, String authHeader ) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        serviceRepository.findById(dto.id()).ifPresent(serviceEntity -> {
            serviceMapper.updateDTO(dto, serviceEntity, userId);
            serviceRepository.save(serviceEntity);
        });
    }

    @Transactional
    public void uploadPictures(MultipartFile servicePic,String authHeader, String serviceId){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        serviceRepository.findById(Long.valueOf(serviceId)).ifPresent(serviceEntity -> {
            if(servicePic != null){
                String url = storageService.uploadPicture(servicePic, "/"+ userId + UUID.randomUUID() +"servicePic");
                serviceEntity.setServicePictureURL(url);
            }
        });
    }

}
