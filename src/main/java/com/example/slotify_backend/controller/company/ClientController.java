package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.ClientDetailsAndHistoryReservationsDTO;
import com.example.slotify_backend.service.company.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client", description = "User clients operations")
@RestController
@RequestMapping("/admin/client")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    @RequestMapping("/{clientId}")
    public ClientDetailsAndHistoryReservationsDTO getClientDetails(@PathVariable Long clientId) {
        return clientService.getClientDetails(clientId);
    }

    @GetMapping
    @RequestMapping("/all")
    public List<ClientDetailsDTO> getAllClientDetails(@RequestHeader("Authorization") String authHeader) {
        return clientService.getAllClientDetails(authHeader);
    }
    @GetMapping
    @RequestMapping("/all/with-reservations")
    public List<ClientDetailsAndHistoryReservationsDTO> getAllClientDetailsAndReservationHistory(@RequestHeader("Authorization") String authHeader) {
        return clientService.getAllClientDetailsAndReservationHistory(authHeader);
    }

}
