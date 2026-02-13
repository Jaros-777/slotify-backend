package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.client.ClientBookingsDTO;
import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.ClientDetailsAndHistoryReservationsDTO;
import com.example.slotify_backend.dto.company.ClientHistoryReservationDTO;
import com.example.slotify_backend.entity.BusinessProfile;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientDetailsMapper {

    private final UserRepository userRepository;

    public ClientDetailsMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClientDetailsDTO toDTO(User user) {
        return  new ClientDetailsDTO(
                null,
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getPictureURL()
                );
    }
    public ClientDetailsAndHistoryReservationsDTO toDetailsAndHistoryReservationDTO(Client client, List<Event> events) {
        List<ClientHistoryReservationDTO> historyReservationDTOs = new ArrayList<>();
        events.forEach(event ->
            historyReservationDTOs.add(new ClientHistoryReservationDTO(
                    event.getId(),
                    event.getClient().getId(),
                    event.getStartDate(),
                    event.getEndDate(),
                    event.getServiceEntity().getName()
            ))
        );


        return  new ClientDetailsAndHistoryReservationsDTO(
                client.getPhone(),
                client.getEmail(),
                client.getName(),
                client.getId(),
                historyReservationDTOs
        );
    }
    public List<ClientDetailsDTO> toDTO(List<Client> clients) {
        return clients.stream()
                .map(client -> new ClientDetailsDTO(
                        client.getId(),
                        client.getName(),
                        client.getEmail(),
                        client.getPhone(),
                        null
                ))
                .collect(Collectors.toList());
    }


    public void updateEntities(ClientDetailsDTO dto, User user, Client client) {
        if (dto.name() != null){
            user.setName(dto.name());
            client.setName(dto.name());
        }
        if (dto.email() != null){
            user.setEmail(dto.email());
            client.setEmail(dto.email());
        }
        if (dto.phone() != null){
            user.setPhone(dto.phone());
            client.setPhone(dto.phone());
        }
    }

    public ClientBookingsDTO toClientBookingDTO(Event event) {
        User businessUser = userRepository.findById(event.getUser().getId()).orElse(null);
        return new ClientBookingsDTO(
                event.getId(),
                event.getStartDate(),
                businessUser.getBusinessProfile().getName(),
                businessUser.getBusinessProfile().getProfilePictureURL(),
                event.getServiceEntity().getName(),
                event.getServiceEntity().getPrice()
        );
    }
}
