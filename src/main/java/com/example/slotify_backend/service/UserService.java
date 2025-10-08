package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.dto.UserRequestRegisterDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.exception.UserAlreadyExistException;
import com.example.slotify_backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(UserRequestRegisterDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException("User already exists");
        }
        userRepository.save(new User(dto.getName(), passwordEncoder.encode(dto.getPassword()), dto.getEmail()));
    }

    public void loginUser(@Valid @RequestBody UserRequestLoginDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {

        }
    }
}
