package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.OrderResponseDTO;
import com.example.slotify_backend.dto.client.PreOrderResponseDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.Role;
import com.example.slotify_backend.exception.EventNotFoundException;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import com.example.slotify_backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BusinessOrderServiceTest {

    @Autowired
    private MockMvc mockMvc;
    private ServiceEntity serviceEntity;
    private ServiceEntity serviceEntity2;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    User user;
    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() throws Exception {
        eventRepository.deleteAll();
        serviceRepository.deleteAll();
        userRepository.deleteAll();
        String uniqueEmail = "test_" + UUID.randomUUID().toString() + "@test.test";

         user = new User(
                "Test",
                 uniqueEmail,
                passwordEncoder.encode("test"),
                Role.USER_COMPANY
        );
        serviceEntity = new ServiceEntity(
                user,
                "Test service",
                10,
                900,
                "",
                true
        );
        serviceEntity2 = new ServiceEntity(
                user,
                "Test service2",
                10,
                900,
                "",
                true
        );
        userRepository.save(user);
        serviceRepository.save(serviceEntity);
        serviceRepository.save(serviceEntity2);

    }


    @Test
    @Transactional
    void shouldReturnTokenAndSaveNewPreOrder() throws Exception {

        LocalDateTime date = LocalDateTime.of(2026, 1, 1, 9, 0);

        PreOrderResponseDTO responseDTO = new PreOrderResponseDTO(
                serviceEntity.getId(),
                date
        );
        String requestBody = objectMapper.writeValueAsString(responseDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/pre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andReturn();

        String reservationToken = result.getResponse().getContentAsString();
        System.out.println("reservationToken: " + reservationToken);
        assertNotNull(reservationToken);
        assertFalse(reservationToken.isEmpty());

        Event preEvent = eventRepository.findByReservationToken(reservationToken).orElseThrow(() -> new EventNotFoundException("Event not found"));
        assertNotNull(preEvent);
        assertEquals(serviceEntity.getId(), preEvent.getServiceEntity().getId());

        assertTrue(preEvent.getTokenExpiryDate().isAfter(LocalDateTime.now()));

    }

    @Test
    @Transactional
    void shouldFindCorrectPreOrderAndSaveOrder() throws Exception {

        String reservationToken = UUID.randomUUID().toString();
        Event preExistedEvent = createAndSavePreOrderEvent(reservationToken,LocalDateTime.now().plusMinutes(5));

        eventRepository.save(preExistedEvent);

        OrderResponseDTO responseDTO = createOrderDto(reservationToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDTO))
                )
                .andExpect(status().isCreated());
        Event event = eventRepository.findByReservationToken(reservationToken).orElseThrow(() -> new EventNotFoundException("Event not found"));
        assertNotNull(event);
        assertEquals(serviceEntity.getId(), event.getServiceEntity().getId());
        assertSame(BookingStatus.TO_BE_CONFIRMED, event.getBookingStatus());
        assertEquals("email@email.com", event.getClient().getEmail());
    }

    @Test
    @Transactional
    void shouldNotSaveTwoPreOrdersForTheSameTimeWithOthersServices() throws Exception {
        LocalDateTime date = LocalDateTime.of(2026, 1, 1, 9, 0);

        PreOrderResponseDTO responseDTO1 = new PreOrderResponseDTO(
                serviceEntity.getId(),
                date
        );
        String requestBody1 = objectMapper.writeValueAsString(responseDTO1);
        PreOrderResponseDTO responseDTO2 = new PreOrderResponseDTO(
                serviceEntity.getId(),
                date
        );
        String requestBody2 = objectMapper.writeValueAsString(responseDTO2);

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/pre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String reservationToken1 = result1.getResponse().getContentAsString();
        System.out.println("reservationToken1: " + reservationToken1);

        assertNotNull(reservationToken1);

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/pre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2)
                )
                .andExpect(status().isConflict())
                .andReturn();



        String reservationToken2 = result2.getResponse().getContentAsString();
        System.out.println("reservationToken2: " + reservationToken2);

    }

    @Test
    @Transactional
    void shouldNotShowChosenTimeAfterPreOrderInOtherService() throws Exception {

        LocalDateTime chosenDate = LocalDateTime.of(2026, 1, 1, 9, 0);
        String url= "/order/booked/" + serviceEntity.getId()+"/" + chosenDate;

        mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().json("[]"));




        Client client = new Client(
                "test",
                "email@email.email"
        );
        clientRepository.save(client);

        Event event = new Event(
                user,
                client,
                serviceEntity2,
                chosenDate,
                chosenDate.plusSeconds(serviceEntity.getDuration()),
                BookingStatus.TO_BE_CONFIRMED,
                ""
        );
        eventRepository.save(event);





        PreOrderResponseDTO responseDTO = new PreOrderResponseDTO(
                serviceEntity.getId(),
                chosenDate
        );
        String requestBody = objectMapper.writeValueAsString(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/pre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isConflict());

    }

    @Test
    void shouldNotSaveTwoPreOrdersInTheSameTime() throws Exception {
        LocalDateTime date = LocalDateTime.of(2026, 1, 1, 9, 0);

        PreOrderResponseDTO responseDTO = new PreOrderResponseDTO(
                serviceEntity.getId(),
                date
        );

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startingGun = new CountDownLatch(1);

        Callable<Integer> performRequestTask = () -> {
            startingGun.await();

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                            .post("/order/pre")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(responseDTO)))
                    .andReturn();

            return result.getResponse().getStatus();
        };

        Future<Integer> user1Response = executor.submit(performRequestTask);
        Future<Integer> user2Response = executor.submit(performRequestTask);

        startingGun.countDown();

        int status1 = user1Response.get();
        int status2 = user2Response.get();

        System.out.println("Status User 1: " + status1);
        System.out.println("Status User 2: " + status2);

        boolean oneSucceeded = (status1 == 201 && status2 == 409) || (status2 == 201 && status1 == 409);
        assertTrue(oneSucceeded);

        executor.shutdown();
    }

    @Test
    @Transactional
    void shouldNotFindCorrectPreOrderBecauseReservationTokenIsInValid() throws Exception {
        String reservationToken = UUID.randomUUID().toString();

        Event preExistedEvent = createAndSavePreOrderEvent(UUID.randomUUID().toString(),LocalDateTime.now().plusMinutes(1));

        eventRepository.save(preExistedEvent);

        OrderResponseDTO responseDTO = createOrderDto(reservationToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDTO))
                )
                .andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    void shouldFindCorrectPreOrderAndReturnTokenExpired() throws Exception {
        String reservationToken = UUID.randomUUID().toString();

        Event preExistedEvent = createAndSavePreOrderEvent(reservationToken,LocalDateTime.now());
        eventRepository.save(preExistedEvent);

        OrderResponseDTO responseDTO = createOrderDto(reservationToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDTO))
                )
                .andExpect(status().isGone());
    }

    private Event createAndSavePreOrderEvent(String reservationToken, LocalDateTime expiryDate) {
        LocalDateTime date = LocalDateTime.of(2026, 1, 1, 9, 0);
        Event event = new Event();
        event.setUser(user);
        event.setServiceEntity(serviceEntity);
        event.setStartDate(date);
        event.setEndDate(date.plusSeconds(serviceEntity.getDuration()));
        event.setBookingStatus(BookingStatus.PENDING);
        event.setReservationToken(reservationToken);
        event.setTokenExpiryDate(expiryDate);

        return eventRepository.save(event);
    }

    private OrderResponseDTO createOrderDto(String reservationToken) {
        return new OrderResponseDTO(
                "Test", "test", "email@email.com", 123123123, "", false, reservationToken
        );
    }

}