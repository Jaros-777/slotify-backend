package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.dto.UserRequestRegisterDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@Valid @RequestBody UserRequestRegisterDTO userRequestRegisterDTO) {
        userService.addUser(userRequestRegisterDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void loginUser(@Valid @RequestBody UserRequestLoginDTO dto) {
        userService.loginUser(dto);
    }
}
