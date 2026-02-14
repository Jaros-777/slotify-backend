package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.ClientBookingsDTO;
import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.entity.Client;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.exception.UserNotFoundException;
import com.example.slotify_backend.mapper.ClientDetailsMapper;
import com.example.slotify_backend.repository.ClientRepository;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.UserRepository;
import com.example.slotify_backend.service.company.JwtService;
import com.example.slotify_backend.service.company.SupabaseStorageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientPanelService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ClientDetailsMapper clientDetailsMapper;
    private final SupabaseStorageService supabaseStorageService;
    private final EventRepository eventRepository;
    private final ClientRepository clientRepository;

    public ClientDetailsDTO getClientDetails(String authHeader) {
        Long clientId = getUserFromAuthHeader(authHeader);
        User client = getUserOrThrow(clientId);

        return clientDetailsMapper.toDTO(client);
    }

    public void updateClientDetails(ClientDetailsDTO clientDetailsDTO, String authHeader) {
        Long clientId = getUserFromAuthHeader(authHeader);
        User userAccount = getUserOrThrow(clientId);
        Client clientAccount = clientRepository.findByUserAccountId(clientId);


        clientDetailsMapper.updateEntities(clientDetailsDTO, userAccount,clientAccount);
        userRepository.save(userAccount);
    }

    @Transactional
    public void uploadPictures(MultipartFile clientPic, String authHeader){
        Long userId = getUserFromAuthHeader(authHeader);
        User client = getUserOrThrow(userId);

            if(clientPic != null && !clientPic.isEmpty()){
                if(client.getPictureURL() != null){
                    supabaseStorageService.deletePictureByPublicUrl(client.getPictureURL());
                }
                String url = supabaseStorageService.uploadPicture(clientPic, "/"+ userId + UUID.randomUUID() +"clientPic");
                client.setPictureURL(url);
            }
    }

    public List<ClientBookingsDTO> getClientBookings(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        Client client = clientRepository.findByUserAccountId(userId);

        List<Event> events = eventRepository.findAllByClientId(client.getId());
        List<ClientBookingsDTO> clientBookingsDTO = new ArrayList<>();
        for (Event event : events) {
            clientBookingsDTO.add(clientDetailsMapper.toClientBookingDTO(event));
        }
        return clientBookingsDTO;

    }

    private User getUserOrThrow(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Long getUserFromAuthHeader(String authHeader){
        return jwtService.getUserIdFromAuthHeader(authHeader);
    }
}
