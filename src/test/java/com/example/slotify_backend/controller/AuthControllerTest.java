package com.example.slotify_backend.controller;


import com.example.slotify_backend.dto.company.UserRequestLoginDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import com.example.slotify_backend.service.company.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.example.slotify_backend.entity.enums.Role.USER_COMPANY;
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
    @Autowired
    JwtService jwtService;

    private User newUser;
    private final String rawPassword = "text";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        newUser = new User(
                "test",
                "test@example.com",
                passwordEncoder.encode(rawPassword),
                USER_COMPANY
        );

    }


    @Test
    void addNewUserSuccess() throws Exception {

        String requestBody = objectMapper.writeValueAsString(newUser);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

    }

    @Test
    void addNewUserFailedUserAlreadyExist() throws Exception {
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

    @Test
    void validateUserSuccess() throws Exception {
        userRepository.save(newUser);
        UserRequestLoginDTO dto = new UserRequestLoginDTO(newUser.getEmail(), rawPassword);
        String requestBody = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String token = objectMapper.readTree(responseBody).get("token").asText();
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/auth/validate")
                    .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }
    @Test
    void validateUserFailed() throws Exception {

        userRepository.save(newUser);


        UserRequestLoginDTO dto = new UserRequestLoginDTO(newUser.getEmail(), rawPassword);
        String requestBody = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();

        String badToken = token.substring(0, token.length() - 1) + "X";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/auth/validate")
                        .header("Authorization", "Bearer " + badToken))
                .andExpect(status().isForbidden());

    }


}