package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.Service;
import com.example.slotify_backend.repository.ServiceRepository;
import com.example.slotify_backend.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EventMapper {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getOwner().getId(),
                event.getName(),
                event.getEmail(),
                event.getPhone(),
                event.getService().getId(),
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
        User owner = userRepository.findById(dto.ownerId()).orElse(null);
        Service service = (Service) serviceRepository.findById(dto.serviceId()).orElse(null);
        return new Event(
                owner,
                dto.name(),
                dto.email(),
                dto.phone(),
                service,
                dto.startDate(),
                dto.endDate(),
                dto.bookingStatus(),
                dto.description()
        );
    }

    public void updateEntity(EventDTO dto, Event event) {
        if (dto.name() != null) event.setName(dto.name());
        if (dto.email() != null) event.setEmail(dto.email());
        if (dto.phone() != null) event.setPhone(dto.phone());

        if (dto.serviceId() != null) {
            Service service = (Service) serviceRepository.findById(dto.serviceId()).orElse(null);
            event.setService(service);
        }

        if (dto.startDate() != null) event.setStartDate(dto.startDate());
        if (dto.endDate() != null) event.setEndDate(dto.endDate());
        if (dto.bookingStatus() != null) event.setBookingStatus(dto.bookingStatus());
        if (dto.description() != null) event.setDescription(dto.description());
    }
}
