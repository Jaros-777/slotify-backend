package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.TokenResponeDTO;
import com.example.slotify_backend.dto.company.UserRequestLoginDTO;
import com.example.slotify_backend.dto.company.UserRequestRegisterDTO;
import com.example.slotify_backend.service.company.AuthService;
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
    public TokenResponeDTO loginUser(@Valid @RequestBody UserRequestLoginDTO dto) {
       return authService.login(dto);
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateUser() {}


}
