package com.example.slotify_backend.service;

import com.example.slotify_backend.dto.TokenRespone;
import com.example.slotify_backend.dto.UserRequestLoginDTO;
import com.example.slotify_backend.dto.UserRequestRegisterDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.exception.UserAlreadyExistException;
import com.example.slotify_backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(UserRequestRegisterDTO dto) {
        if(userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistException("User already exists");
        }
        userRepository.save(new User(dto.name(), passwordEncoder.encode(dto.password()), dto.email()));
    }

    public TokenRespone login (@Valid @RequestBody UserRequestLoginDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(user.getEmail());
        return new TokenRespone(token);
    }
}
