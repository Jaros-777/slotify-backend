package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.BookedHoursEventDTO;
import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.dto.client.OrderResponseDTO;
import com.example.slotify_backend.dto.client.PreOrderResponseDTO;
import com.example.slotify_backend.entity.*;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.NotificationType;
import com.example.slotify_backend.exception.EventNotFoundException;
import com.example.slotify_backend.mapper.OrderMapper;
import com.example.slotify_backend.repository.*;
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
public class BussinessOrderService {
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final VacationRepository vacationRepository;
    private final NotificationRepository notificationRepository;

    public OrderDTO getAllDetailsByServiceId(Long serviceId) {
        return orderMapper.toDTO(serviceId);
    }

    public String preOrder(PreOrderResponseDTO orderDTO) {
        Event event = new Event();
        ServiceEntity service = serviceRepository.findById(orderDTO.serviceId()).orElseThrow(()-> new RuntimeException("Service not found"));
        event.setServiceEntity(service);

        User user = userRepository.findById(service.getUser().getId()).orElseThrow(()-> new RuntimeException("User not found"));
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

    public void insertOrder( OrderResponseDTO orderDTO) {

            Event event = eventRepository.findByReservationToken(orderDTO.reservationToken()).orElseThrow(()-> new EventNotFoundException("Event not found"));

            if(event.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
               throw new ResponseStatusException(HttpStatus.GONE, "Reservation token is expired");
            }

            List<Event> oldEvents= eventRepository.findAllByBookingStatusAndTokenExpiryDateBefore(BookingStatus.PENDING, LocalDateTime.now());
            eventRepository.deleteAll(oldEvents);

            Client client = clientRepository.findByEmail(orderDTO.email());
            if(client == null){
                client = new Client();
                client.setName(orderDTO.firstName() +" "+ orderDTO.lastName());
                client.setEmail(orderDTO.email());
                client.setPhone(orderDTO.phone());
                clientRepository.save(client);
            }
            event.setClient(client);
//            ServiceEntity service = serviceRepository.findById(orderDTO.serviceId()).orElseThrow(()-> new RuntimeException("Service not found"));
//            event.setServiceEntity(service);
//
//            User user = userRepository.findById(service.getUser().getId()).orElseThrow(()-> new RuntimeException("User not found"));
//            event.setUser(user);
//
//            event.setStartDate(orderDTO.chosenDate());
//
//            LocalDateTime endTime = orderDTO.chosenDate().plusSeconds(service.getDuration());

//            event.setEndDate(endTime);
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

    public List<BookedHoursEventDTO> getBookedHoursEvents (Long serviceId, LocalDateTime chosenDay) {
        LocalDateTime startOfDay = chosenDay.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = chosenDay.withHour(23).withMinute(59).withSecond(59);

        List<Event> bookedEvents = eventRepository.findAllByServiceEntityIdAndStartDateBetween(serviceId, startOfDay,endOfDay);
        Long userId = serviceRepository.findById(serviceId).get().getUser().getId();
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
