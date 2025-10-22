package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.ServiceEntity;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.Role;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.ServiceRepository;
import com.example.slotify_backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String userToken;

    private User user;
    private Client client;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    private ServiceEntity service1;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EventRepository eventRepository;

    @BeforeAll
    void setUp() throws Exception {

        user = new User("Test", "test@test.pl",
                encoder.encode("password"), Role.USER_COMPANY);
        userRepository.saveAndFlush(user);

        client = new Client("client");
        clientRepository.saveAndFlush(client);

        service1 = new ServiceEntity(user, "Repair car", 200, 3600);
        serviceRepository.saveAndFlush(service1);


        UserRequestLoginDTO dto = new UserRequestLoginDTO(user.getEmail(), "password");
        String requestBody = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        userToken = objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();
    }


    @Test
    void addNewEventSuccess() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusDays(service1.getDuration());
        BookingStatus status = BookingStatus.CONFIRMED;

        EventCreateDTO dto = new EventCreateDTO(
                user.getId(),
                client.getId(),
                service1.getId(),
                now,
                end,
                status,
                "Main client"
        );

        String requestBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());


    }

    @Test
    void showAllUserEvents() throws Exception {
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        LocalDateTime end = now.plusSeconds(service1.getDuration());

        Event event1 = new Event(
                user,
                client,
                service1,
                now,
                end,
                BookingStatus.CONFIRMED,
                "Desc"
        );
        Event event2 = new Event(
                user,
                client,
                service1,
                now,
                end,
                BookingStatus.CONFIRMED,
                "Desc"
        );
        Event event3 = new Event(
                user,
                client,
                service1,
                now,
                end,
                BookingStatus.CONFIRMED,
                "Desc"
        );

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/events")
                        .header("Authorization", "Bearer " + userToken)
                        .param("id", user.getId().toString())
                        .param("startWeek", now.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].clientId").value(1))
                .andExpect(jsonPath("$[0].serviceId").value(1))
                .andExpect(jsonPath("$[0].bookingStatus").value("CONFIRMED"))
                .andExpect(jsonPath("$[0].description").value("Desc"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].userId").value(1))
                .andExpect(jsonPath("$[1].description").value("Desc"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].userId").value(1))
                .andExpect(jsonPath("$[2].description").value("Desc"));
    }
}