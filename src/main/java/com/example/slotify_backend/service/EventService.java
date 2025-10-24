package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.EventDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final JwtService jwtService;
    private final ClientRepository clientRepository;

    public List<EventDTO> getAllUserEventsInWeek(String authHeader,LocalDateTime startWeek){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        LocalDateTime endDate = startWeek.plusDays(6).plusHours(23).plusMinutes(59).plusSeconds(59);
        List<Event> events = eventRepository.findAllByUserIdAndStartDateBetween(userId,startWeek,endDate);

        return eventMapper.toDTO(events);
    }

    public void createNewEvent(EventCreateDTO dto,String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        Long clientId = dto.clientId();

        if(dto.clientId() == null){
            Client client = new Client(
                    dto.clientName(),
                    dto.clientEmail()
            );
            if(dto.clientPhone() != null){
                client.setPhone(dto.clientPhone());
            }
            clientRepository.save(client);
            clientId = clientRepository.findByEmail(dto.clientEmail()).getId();
        }
        eventRepository.save(eventMapper.toEntity(dto,userId, clientId));
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
