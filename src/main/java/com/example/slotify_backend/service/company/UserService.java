package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.UserDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserDTO getUser(String authHeader){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        User user = userRepository.findById(userId).orElse(null);
        return new UserDTO(
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public void updateUser(String authHeader,UserDTO userDTO){
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        User user = userRepository.findById(userId).orElse(null);
        if(userDTO.name() != null){
            user.setName(userDTO.name());
        }
        if(userDTO.email() != null){
            user.setEmail(userDTO.email());
        }
    }
}
