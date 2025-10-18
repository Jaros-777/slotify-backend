package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.AllUserEventsInWeekDTO;
import com.example.slotify_backend.dto.EventDTO;
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

    @GetMapping
    public List<EventDTO> getAllUserEventsInWeek(@RequestParam Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startWeek) {
        return eventService.getAllUserEventsInWeek(id,startWeek);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewEvent(@Valid @RequestBody EventCreateDTO dto) {
        eventService.createNewEvent(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent (@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/update")
    public void updateEvent(@Valid @RequestBody EventDTO dto) {
        eventService.updateEvent(dto);
    }
}
