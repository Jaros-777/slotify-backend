package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.ServiceCreateDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import com.example.slotify_backend.service.company.ServiceService;
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
    public List<ServiceDTO> getAllServicesByUser( @RequestHeader(value = "Authorization") String authHeader){
       return serviceService.getAllServicesByUser(authHeader);
    };

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewService(@Valid @RequestBody ServiceCreateDTO dto, @RequestHeader("Authorization") String authHeader) {
        serviceService.createNewService(dto, authHeader);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        serviceService.deleteServiceById(id, authHeader);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateService(@Valid @RequestBody ServiceDTO dto,@RequestHeader("Authorization") String authHeader) {
         serviceService.updateServiceById(dto,authHeader);
    }
}
