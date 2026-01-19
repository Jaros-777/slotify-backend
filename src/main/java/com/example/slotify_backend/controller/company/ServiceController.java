package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.ServiceCreateDTO;
import com.example.slotify_backend.dto.company.ServiceDTO;
import com.example.slotify_backend.service.company.ServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Service", description = "Service operations")
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

    @PostMapping("/picture")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadPicture(
            @RequestPart(value = "servicePic", required = false) MultipartFile servicePic,
            @RequestPart(value = "id", required = false) String serviceId,
            @RequestHeader("Authorization") String authHeader){
        serviceService.uploadPictures(servicePic,authHeader,serviceId);
    }
}
