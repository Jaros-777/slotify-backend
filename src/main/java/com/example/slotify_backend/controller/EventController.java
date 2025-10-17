package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.dto.EventOwnerIdDTO;
import com.example.slotify_backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    EventService eventService;

    @GetMapping
    public List<EventDTO> getEvents(@RequestBody EventOwnerIdDTO dto) {
        return eventService.getAllEvents(dto);
    }

}
