package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.EventCreateDTO;
import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.Role;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AllArgsConstructor
class EventControllerTest {

    private MockMvc mockMvc;
    private MvcResult userToken;

    private User user;
    private PasswordEncoder encoder;
    private ObjectMapper objectMapper;
    private UserRepository userRepository;
    private EventMapper eventMapper;

    @BeforeEach
    void setUp() throws Exception {
        user = new User(
                "Test",
                "test@test.pl",
                encoder.encode("password"),
                Role.USER_COMPANY
        );

        userRepository.save(user);

        UserRequestLoginDTO dto = new UserRequestLoginDTO(user.getEmail(), "password");

        String requestBody = objectMapper.writeValueAsString(dto);


        userToken = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();
    }

//    @Test
//    void addNewEventSuccess() throws Exception {
//            EventCreateDTO dto = new EventCreateDTO(
//                    user.getId(),
//                    user.getName(),
//                    user.getEmail(),
//                    user.getPhone
//            );
//        @NotNull
//        Long ownerId,
//        @NotBlank
//        String name,
//        @Email
//        String email,
//        Integer phone,
//        Long serviceId,
//        @NotNull
//        LocalDateTime startDate,
//        @NotNull
//        LocalDateTime endDate,
//        @NotNull
//        @Enumerated(EnumType.STRING)
//        BookingStatus bookingStatus,
//        String description
//    }
}