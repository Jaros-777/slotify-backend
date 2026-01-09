package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.ClientDetailsAndHistoryReservationsDTO;
import com.example.slotify_backend.dto.company.ClientHistoryReservationDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.mapper.ClientDetailsMapper;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private final JwtService jwtService;
    private final EventRepository eventRepository;
    private final ClientDetailsMapper clientDetailsMapper;
    private final ClientRepository clientRepository;

    public ClientDetailsAndHistoryReservationsDTO getClientDetails(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        List<Event> events = new ArrayList<>(eventRepository.findAllByClientId(client.getId()));

        return clientDetailsMapper.toDetailsAndHistoryReservationDTO(client, events);

    }

    ;

    public List<ClientDetailsDTO> getAllClientDetails(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        List<Client> clients = new ArrayList<>();
        eventRepository.findAllByUserIdAndBookingStatusNot(userId, BookingStatus.VACATION).forEach(event -> {
            Client client = event.getClient();
            if (!clients.contains(client)) {
                clients.add(client);
            }
        });

        return clientDetailsMapper.toDTO(clients);
    }

    ;

    public List<ClientDetailsAndHistoryReservationsDTO> getAllClientDetailsAndReservationHistory(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        List<Client> clients = new ArrayList<>();
        eventRepository.findAllByUserIdAndBookingStatusNot(userId, BookingStatus.VACATION).forEach(event -> {
            Client client = event.getClient();
            if (!clients.contains(client)) {
                clients.add(client);
            }
        });

        List<ClientDetailsAndHistoryReservationsDTO> dtos = new ArrayList<>();
        clients.forEach(client -> {
                List<Event> evets = client.getEvents();
            System.out.println(evets.size());
                dtos.add(clientDetailsMapper.toDetailsAndHistoryReservationDTO(client, evets));
        });

        return dtos;
    }

    ;
}
