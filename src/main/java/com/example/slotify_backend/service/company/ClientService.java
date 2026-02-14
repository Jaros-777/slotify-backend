package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.ClientDetailsAndHistoryReservationsDTO;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {
    private final JwtService jwtService;
    private final EventRepository eventRepository;
    private final ClientDetailsMapper clientDetailsMapper;
    private final ClientRepository clientRepository;

    public ClientDetailsAndHistoryReservationsDTO getClientDetails(String authHeader,Long clientId) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        List<Event> events = new ArrayList<>(eventRepository.findAllByClientIdAndUserId(client.getId(),userId));

        return clientDetailsMapper.toDetailsAndHistoryReservationDTO(client, events);

    }

    public List<ClientDetailsDTO> getAllClientDetails(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        List<Client> clients = new ArrayList<>();
        eventRepository.findAllByUserIdAndBookingStatusNot(userId, BookingStatus.VACATION).forEach(event -> {
            Client client = event.getClient();
            if (!clients.contains(client)) {
                clients.add(client);
            }
        });

        return clientDetailsMapper.toDTO(clients);
    }



    public List<ClientDetailsAndHistoryReservationsDTO> getAllClientDetailsAndReservationHistory(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        List<Client> clients = new ArrayList<>();
        eventRepository.findAllByUserIdAndBookingStatusNot(userId, BookingStatus.VACATION).forEach(event -> {
            Client client = event.getClient();
            if (!clients.contains(client)) {
                clients.add(client);
            }
        });

        List<ClientDetailsAndHistoryReservationsDTO> dtos = new ArrayList<>();
        clients.forEach(client -> {
                List<Event> events = client.getEvents().stream()
                        .filter(event -> event.getUser().getId().equals(userId))
                        .collect(Collectors.toList());
                dtos.add(clientDetailsMapper.toDetailsAndHistoryReservationDTO(client, events));
        });

        return dtos;
    }


}
