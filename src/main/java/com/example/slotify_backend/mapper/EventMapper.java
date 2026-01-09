package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.EventCreateDTO;
import com.example.slotify_backend.dto.company.EventDTO;
import com.example.slotify_backend.dto.company.VacationDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.ServiceRepository;

import com.example.slotify_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EventMapper {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public EventDTO toDTO(Event event) {

        if(event.getClient() != null){
            return new EventDTO(
                    event.getId(),
                    event.getClient().getName(),
                    event.getClient().getEmail(),
                    event.getClient().getPhone(),
                    event.getServiceEntity().getId(),
                    event.getStartDate(),
                    event.getEndDate(),
                    event.getBookingStatus(),
                    event.getDescription()
            );
        }else{
            return new EventDTO(
                    event.getId(),
                    null,
                    null,
                    null,
                    null,
                    event.getStartDate(),
                    event.getEndDate(),
                    event.getBookingStatus(),
                    event.getDescription()
            );
        }
    }

    public List<EventDTO> toDTO(List<Event> events) {
        return events.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Event toEntity(EventCreateDTO dto, Long userId, Client client) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ServiceEntity serviceEntity =serviceRepository.findById(dto.serviceId()).orElseThrow(() -> new RuntimeException("Service not found"));
        return new Event(
                user,
                client,
                serviceEntity,
                dto.startDate(),
                dto.endDate(),
                dto.bookingStatus(),
                dto.description()
        );
    }

    public VacationDTO toVacationDTO(Event event) {

        return new VacationDTO(
                event.getId(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate()
        );
    }
    public Event toEntity(VacationDTO dto, User user){
        return new Event(
                user,
                dto.startDate(),
                dto.endDate(),
                dto.name(),
                BookingStatus.VACATION
        );
    }

    public void updateEntity(EventDTO dto, Event event) {

        if (dto.serviceId() != null) {
            ServiceEntity serviceEntity = serviceRepository.findById(dto.serviceId())
                    .orElseThrow(() -> new EntityNotFoundException("Service with id " + dto.serviceId() + " not found"));
            event.setServiceEntity(serviceEntity);
        }

        if (dto.startDate() != null) event.setStartDate(dto.startDate());
        if (dto.endDate() != null) event.setEndDate(dto.endDate());
        if (dto.bookingStatus() != null) event.setBookingStatus(dto.bookingStatus());
        if (dto.description() != null) event.setDescription(dto.description());
    }

}
