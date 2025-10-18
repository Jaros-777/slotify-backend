package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    EventRepository eventRepository;
    EventMapper eventMapper;

    public List<EventDTO> getAllUserEventsInWeek(Long id,LocalDateTime startWeek){
        LocalDateTime endDate = startWeek.plusDays(6).plusHours(23).plusMinutes(59).plusSeconds(59);
        List<Event> events = eventRepository.findAllByOwnerIdAndStartDateBetween(id,startWeek,endDate);

        return eventMapper.toDTO(events);
    }

    public void createNewEvent(EventCreateDTO dto) {
        eventRepository.save(eventMapper.toEntity(dto));
    };

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void updateEvent(EventDTO dto) {
        eventRepository.findById(dto.id()).ifPresent(event -> {
            eventMapper.updateEntity(dto, event);
            eventRepository.save(event);
        });
    }
}
