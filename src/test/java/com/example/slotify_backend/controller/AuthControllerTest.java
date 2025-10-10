package com.example.slotify_backend.controller;


import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.dto.UserRequestRegisterDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import com.example.slotify_backend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.convention.TestBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User newUser;
    private final String rawPassword = "text";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        newUser = new User(
                "test",
                "test@example.com",
                passwordEncoder.encode(rawPassword), // <--- tutaj hasło jest haszowane
                "USER_COMPANY"
        );
    }


    @Test
    void addUserSuccess() throws Exception {

        String requestBody = objectMapper.writeValueAsString(newUser);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

    }
    @Test
    void addUserFailedUserAlreadyExist() throws Exception {
        userRepository.save(newUser);

        String requestBody = objectMapper.writeValueAsString(newUser);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());

    }
    @Test
    void loginSuccesful() throws Exception {
        userRepository.save(newUser);

        UserRequestLoginDTO dto = new UserRequestLoginDTO(newUser.getEmail(), rawPassword);

        String requestBody = objectMapper.writeValueAsString(dto);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").isNotEmpty());

    }
    @Test
    void loginFailedUserNotFound() throws Exception {

        UserRequestLoginDTO dto = new UserRequestLoginDTO("bad@email.com", rawPassword);

        String requestBody = objectMapper.writeValueAsString(dto);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());

    }
    @Test
    void loginFailedWrongPassword() throws Exception {
        userRepository.save(newUser);

        UserRequestLoginDTO dto = new UserRequestLoginDTO(newUser.getEmail(), "wrongPassword");

        String requestBody = objectMapper.writeValueAsString(dto);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());

    }

}