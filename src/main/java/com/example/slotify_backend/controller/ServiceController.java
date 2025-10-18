package com.example.slotify_backend.controller;

import com.example.slotify_backend.dto.ServiceCreateDTO;
import com.example.slotify_backend.dto.ServiceDTO;
import com.example.slotify_backend.service.ServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    ServiceService serviceService;

    @GetMapping
    public List<ServiceDTO> getAllServicesByUser(@RequestParam Long userId){
       return serviceService.getAllServicesByUser(userId);
    };

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewService(@Valid @RequestBody ServiceCreateDTO dto) {
        serviceService.createNewService(dto);
    }
}
