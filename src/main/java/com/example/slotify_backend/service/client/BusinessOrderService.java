package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.BookedHoursEventDTO;
import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.dto.client.OrderResponseDTO;
import com.example.slotify_backend.dto.client.PreOrderResponseDTO;
import com.example.slotify_backend.entity.*;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.NotificationType;
import com.example.slotify_backend.exception.EventAlreadyBookedException;
import com.example.slotify_backend.exception.EventNotFoundException;
import com.example.slotify_backend.exception.ResourceNotFoundException;
import com.example.slotify_backend.exception.TokenExpiredException;
import com.example.slotify_backend.mapper.OrderMapper;
import com.example.slotify_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class BusinessOrderService {
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final NotificationRepository notificationRepository;

    public OrderDTO getAllDetailsByServiceId(Long serviceId) {
        return orderMapper.toDTO(serviceId);
    }

    @Transactional
    public String preOrder(PreOrderResponseDTO orderDTO) {

        ServiceEntity service = serviceRepository.findById(orderDTO.serviceId()).orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        User user = userRepository.findByIdWithPessimisticLock(orderDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        boolean existEvent = eventRepository.existsOverlappingEventForUser(user.getId(), orderDTO.chosenDate(), orderDTO.chosenDate().plusSeconds(service.getDuration()));
        if (existEvent) {
            throw new EventAlreadyBookedException("Event already exists");
        }

        Event event = new Event();
        event.setServiceEntity(service);

        event.setUser(user);

        event.setStartDate(orderDTO.chosenDate());
        LocalDateTime endTime = orderDTO.chosenDate().plusSeconds(service.getDuration());
        event.setEndDate(endTime);

        event.setBookingStatus(BookingStatus.PENDING);
        String reservationToken = UUID.randomUUID().toString();
        event.setReservationToken(reservationToken);
        event.setTokenExpiryDate(LocalDateTime.now().plusMinutes(5));

        eventRepository.save(event);
        return reservationToken;
    }

    @Transactional
    public void insertOrder(OrderResponseDTO orderDTO) {

        Event event = eventRepository.findByReservationToken(orderDTO.reservationToken()).orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (event.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }

        //This function must be here, because @Schedule will cost to more for server who has only 512MB of ram.
        eventRepository.deleteExpiredEvents(BookingStatus.PENDING, LocalDateTime.now());

        Client client = clientRepository.findByEmail(orderDTO.email());
        if (client == null) {
            client = new Client();
            client.setName(orderDTO.firstName() + " " + orderDTO.lastName());
            client.setEmail(orderDTO.email());
            client.setPhone(orderDTO.phone());
            clientRepository.save(client);
        }
        event.setClient(client);
        event.setBookingStatus(BookingStatus.TO_BE_CONFIRMED);
        event.setDescription(orderDTO.description());
        eventRepository.save(event);


        Notification notification = new Notification(
                false,
                event.getServiceEntity(),
                event,
                LocalDateTime.now(),
                NotificationType.BOOKING,
                client,
                event.getUser()
        );
        notificationRepository.save(notification);
    }

    public List<BookedHoursEventDTO> getBookedHoursEvents(Long userId, LocalDateTime chosenDay) {
        LocalDateTime startOfDay = chosenDay.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = chosenDay.withHour(23).withMinute(59).withSecond(59);

        List<Event> bookedEvents = eventRepository.findAllByUserIdAndStartDateBetween(userId, startOfDay, endOfDay);
        List<Event> vacations = eventRepository.findAllByUserIdAndStartDateBetweenAndBookingStatus(userId, startOfDay, endOfDay, BookingStatus.VACATION);
        List<BookedHoursEventDTO> bookedHoursEvents = new ArrayList<>();

        bookedEvents.forEach(event ->
                bookedHoursEvents.add(new BookedHoursEventDTO(event.getStartDate(), event.getEndDate()))
        );
        vacations.forEach(vacation ->
                bookedHoursEvents.add(new BookedHoursEventDTO(vacation.getStartDate(), vacation.getEndDate()))
        );


        return bookedHoursEvents;

    }
}
