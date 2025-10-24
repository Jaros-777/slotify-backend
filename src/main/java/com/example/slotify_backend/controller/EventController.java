package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.AllUserEventsInWeekDTO;
import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/{startWeek}")
    public List<EventDTO> getAllUserEventsInWeek(@RequestHeader("Authorization") String authHeader, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startWeek) {
        return eventService.getAllUserEventsInWeek(authHeader,startWeek);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewEvent(@Valid @RequestBody EventCreateDTO dto,@RequestHeader("Authorization") String authHeader) {
        eventService.createNewEvent(dto, authHeader);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent (@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEvent(@Valid @RequestBody EventDTO dto) {
        eventService.updateEvent(dto);
    }
}
