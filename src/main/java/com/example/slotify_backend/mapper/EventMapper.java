package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.ServiceRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EventMapper {

    private final ServiceRepository serviceRepository;
    private final ClientRepository clientRepository;

    public EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getOwner().getId(),
                event.getClient().getId(),
                event.getServiceEntity().getId(),
                event.getStartDate(),
                event.getEndDate(),
                event.getBookingStatus(),
                event.getDescription()
        );
    }

    public List<EventDTO> toDTO(List<Event> events) {
        return events.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Event toEntity(EventCreateDTO dto) {
        Client client = clientRepository.findById(dto.clientId()).orElse(null);
        ServiceEntity serviceEntity =serviceRepository.findById(dto.serviceId()).orElse(null);
        return new Event(
                client,
                serviceEntity,
                dto.startDate(),
                dto.endDate(),
                dto.bookingStatus(),
                dto.description()
        );
    }

    public void updateEntity(EventDTO dto, Event event) {
        if (dto.clientId() != null) {
            Client client =  clientRepository.findById(dto.clientId()).orElse(null);
            event.setClient(client);
        }

        if (dto.serviceId() != null) {
            ServiceEntity serviceEntity = serviceRepository.findById(dto.serviceId()).orElse(null);
            event.setServiceEntity(serviceEntity);
        }

        if (dto.startDate() != null) event.setStartDate(dto.startDate());
        if (dto.endDate() != null) event.setEndDate(dto.endDate());
        if (dto.bookingStatus() != null) event.setBookingStatus(dto.bookingStatus());
        if (dto.description() != null) event.setDescription(dto.description());
    }

}
