package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.UserDTO;
import com.example.slotify_backend.service.company.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDTO getUser(@RequestHeader("Authorization") String authHeader) {
        return userService.getUser(authHeader);
    }

    @PutMapping
    public void updateUser(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody UserDTO userDTO) {
         userService.updateUser(authHeader,userDTO);
    }
}
