package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.UserDTO;
import com.example.slotify_backend.service.company.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User profile operations")
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
