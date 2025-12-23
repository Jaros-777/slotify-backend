package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.mapper.ClientDetailsMapper;
import com.example.slotify_backend.repository.UserRepository;
import com.example.slotify_backend.service.company.JwtService;
import com.example.slotify_backend.service.company.SupabaseStorageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientPanelService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ClientDetailsMapper clientDetailsMapper;
    private final SupabaseStorageService supabaseStorageService;

    public ClientDetailsDTO getClientDetails(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long clientId = jwtService.getUserIdFromToken(token);
        User client = userRepository.findById(clientId).orElseThrow(() -> new RuntimeException("User not found") );

        return clientDetailsMapper.toDTO(client);
    }

    public void updateClientDetails(ClientDetailsDTO clientDetailsDTO, String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long clientId = jwtService.getUserIdFromToken(token);
        User client = userRepository.findById(clientId).orElseThrow(() -> new RuntimeException("User not found"));
        clientDetailsMapper.updateDTO(clientDetailsDTO, client);
        userRepository.save(client);
    }

    @Transactional
    public void uploadPictures(MultipartFile clientPic, String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        User client = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            if(clientPic != null){
                String url = supabaseStorageService.uploadPicture(clientPic, "/"+ userId + UUID.randomUUID() +"clientPic");
                client.setPictureURL(url);
            }
    }
}
