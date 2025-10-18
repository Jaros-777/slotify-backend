package com.example.slotify_backend.controller;

import com.example.slotify_backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private User user;
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        user = new User(
                "Test",
            "test@test.pl",
        encoder.encode("password"),
        role = role
        );
    }

    @Test
    void addNewEventSuccess() throws Exception {

    }
}