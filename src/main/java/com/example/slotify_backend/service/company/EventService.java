package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.EventCreateDTO;
import com.example.slotify_backend.dto.company.EventDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final JwtService jwtService;
    private final ClientRepository clientRepository;

    public List<EventDTO> getAllUserEventsInWeek(String authHeader,LocalDateTime startWeek){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        LocalDateTime endDate = startWeek.plusDays(6).plusHours(23).plusMinutes(59).plusSeconds(59);
        List<Event> events = eventRepository.findAllByUserIdAndStartDateBetween(userId,startWeek,endDate);

        return eventMapper.toDTO(events);
    }

    public void createNewEvent(EventCreateDTO dto,String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        Client client = clientRepository.findByEmail(dto.clientEmail());

        if(client == null){
            client = new Client(
                    dto.clientName(),
                    dto.clientEmail()
            );
            if(dto.clientPhone() != null){
                client.setPhone(dto.clientPhone());
            }
            clientRepository.save(client);
        }
        eventRepository.save(eventMapper.toEntity(dto,userId, client));
    };

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public void updateEvent(EventDTO dto) {
        Event currentEvent = eventRepository.findById(dto.id()).get();
        Client clientFromDTO = clientRepository.findByEmail(dto.clientEmail());


        if(clientFromDTO == null){
            clientFromDTO = new Client(
                    dto.clientName(),
                    dto.clientEmail());
            if(dto.clientPhone() != null){
                clientFromDTO.setPhone(dto.clientPhone());
            }
            clientRepository.save(clientFromDTO);
            currentEvent.setClient(clientFromDTO);
        }else{
            if(!Objects.equals(dto.clientName(), clientFromDTO.getName())){
                clientFromDTO.setName(dto.clientName());
            }
            if(!Objects.equals(dto.clientPhone(), clientFromDTO.getPhone())){
                clientFromDTO.setPhone(dto.clientPhone());
            }
        }

        eventMapper.updateEntity(dto, currentEvent);
    }
}
