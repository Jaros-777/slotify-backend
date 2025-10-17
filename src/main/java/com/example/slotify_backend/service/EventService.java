package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.dto.EventOwnerIdDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EventService {

    EventRepository eventRepository;

    public List<EventDTO> getAllEvents(EventOwnerIdDTO dto){
        List<Event> events = eventRepository.findAllByOwnerId(dto.ownerId());
        List<EventDTO> eventDTOs = new ArrayList<>();

        return eventDTOs;
    }
}
