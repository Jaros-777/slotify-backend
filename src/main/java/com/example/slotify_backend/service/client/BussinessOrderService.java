package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.BookedHoursEventDTO;
import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.dto.client.OrderResponseDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.mapper.OrderMapper;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import com.example.slotify_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BussinessOrderService {
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public OrderDTO getAllDetailsByServiceId(Long serviceId) {
        return orderMapper.toDTO(serviceId);
    }

    public void insertOrder( OrderResponseDTO orderDTO) {

            Client client = clientRepository.findByEmail(orderDTO.email());
            if(client == null){
                client = new Client();
                client.setName(orderDTO.firstName() +" "+ orderDTO.lastName());
                client.setEmail(orderDTO.email());
                client.setPhone(orderDTO.phone());
                clientRepository.save(client);
            }
            Event event = new Event();
            event.setClient(client);
            ServiceEntity service = serviceRepository.findById(orderDTO.serviceId()).orElseThrow(()-> new RuntimeException("Service not found"));
            event.setServiceEntity(service);

            User user = userRepository.findById(service.getUser().getId()).orElseThrow(()-> new RuntimeException("User not found"));
            event.setUser(user);

            event.setStartDate(orderDTO.chosenDate());

            LocalDateTime endTime = orderDTO.chosenDate().plusSeconds(service.getDuration());

            event.setEndDate(endTime);
            event.setBookingStatus(BookingStatus.TO_BE_CONFIRMED);
            event.setDescription(orderDTO.description());
            eventRepository.save(event);
    }

    public List<BookedHoursEventDTO> getBookedHoursEvents (Long serviceId, LocalDateTime chosenDay) {
        LocalDateTime startOfDay = chosenDay.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = chosenDay.withHour(23).withMinute(59).withSecond(59);

        List<Event> bookedEvents = eventRepository.findAllByServiceEntityIdAndStartDateBetween(serviceId, startOfDay,endOfDay);
        List<BookedHoursEventDTO> bookedHoursEvents = new ArrayList<>();

        bookedEvents.forEach(event -> {
            bookedHoursEvents.add(new BookedHoursEventDTO(event.getStartDate()));
        });



        return bookedHoursEvents;

    }
}
