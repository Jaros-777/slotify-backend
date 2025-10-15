package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.TokenRespone;
import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.dto.UserRequestRegisterDTO;
import com.example.slotify_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@Valid @RequestBody UserRequestRegisterDTO userRequestRegisterDTO) {
        authService.addUser(userRequestRegisterDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenRespone loginUser(@Valid @RequestBody UserRequestLoginDTO dto) {
       return authService.login(dto);
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateUser() {}
}
