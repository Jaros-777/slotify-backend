package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.ServiceCreateDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.Notification;
import com.example.slotify_backend.mapper.ServiceMapper;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.NotificationRepository;
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
    private final NotificationRepository notificationRepository;

    public List<ServiceDTO> getAllServicesByUser(String authHeader) {

        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        List<ServiceEntity> services = serviceRepository.findAllByUserId(userId);
        return serviceMapper.toDTO(services);
    }

    public Long createNewService(ServiceCreateDTO dto, String authHeader ) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        ServiceEntity service = serviceMapper.toEntity(dto, userId, true);
        serviceRepository.save(service);
        return service.getId();
    }

    @Transactional
    public void deleteServiceById(Long serviceId, String authHeader) {
        List<Event> eventsList = eventRepository.findAllEventsByServiceEntity_Id(serviceId);


        if (!eventsList.isEmpty()) {
            Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

            ServiceEntity defaultService = serviceRepository.findByIsEditableAndUserId(false, userId);

            for (Event event : eventsList) {
                event.setServiceEntity(defaultService);
            }
            List<Notification> notifications = notificationRepository.findAllByService_Id(serviceId);
            notificationRepository.deleteAll(notifications);
        }

        serviceRepository.deleteById(serviceId);
    }

    public void updateServiceById(ServiceDTO dto, String authHeader ) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        serviceRepository.findById(dto.id()).ifPresent(serviceEntity -> {
            serviceMapper.updateDTO(dto, serviceEntity, userId);
            serviceRepository.save(serviceEntity);
        });
    }

    @Transactional
    public void uploadPictures(MultipartFile servicePic,String authHeader, String serviceId){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        serviceRepository.findById(Long.valueOf(serviceId)).ifPresent(serviceEntity -> {
            if(servicePic != null){
                if(serviceEntity.getServicePictureURL() != null){
                    storageService.deletePictureByPublicUrl(serviceEntity.getServicePictureURL());
                }
                String url = storageService.uploadPicture(servicePic, "/"+ userId + UUID.randomUUID() +"servicePic");
                serviceEntity.setServicePictureURL(url);
            }
        });
    }

}
