package com.example.slotify_backend.controller.client;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.ServiceCreateDTO;
import com.example.slotify_backend.service.client.ClientPanelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientPanelController {
    private final ClientPanelService clientPanelService;

    @GetMapping
    public ClientDetailsDTO getClientDetails(@RequestHeader("Authorization") String authHeader) {
        return clientPanelService.getClientDetails(authHeader);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateClientDetails(@Valid @RequestBody ClientDetailsDTO dto, @RequestHeader("Authorization") String authHeader) {
        clientPanelService.updateClientDetails(dto, authHeader);
    }

    @PostMapping("/picture")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadPicture(
            @RequestPart(value = "clientPic", required = false) MultipartFile clientPic,
            @RequestHeader("Authorization") String authHeader){
        clientPanelService.uploadPictures(clientPic,authHeader);
    }

}
