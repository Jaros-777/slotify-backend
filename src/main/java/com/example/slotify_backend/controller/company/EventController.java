package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.EventCreateDTO;
import com.example.slotify_backend.dto.company.EventDTO;
import com.example.slotify_backend.service.company.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Event", description = "Event operations")
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

    @DeleteMapping("/delete/{id}")
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
