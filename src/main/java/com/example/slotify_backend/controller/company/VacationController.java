package com.example.slotify_backend.controller.company;

import com.example.slotify_backend.dto.company.VacationDTO;
import com.example.slotify_backend.service.company.VacationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Vacation", description = "User vacations operations")
@RestController
@RequestMapping("/vacation")
@AllArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @GetMapping
    public List<VacationDTO> getVacationList(@RequestHeader("Authorization") String authHeader){
        return vacationService.getVacationList(authHeader);
    }

    @PostMapping
    public void createUpdateVacation(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody VacationDTO vacationDTO){
        System.out.println(vacationDTO);
         vacationService.createUpdateVacation(authHeader, vacationDTO);
    }

    @GetMapping("/{vacationId}")
    @ResponseStatus(HttpStatus.OK)
    public void getVacationList( @PathVariable Long vacationId){
         vacationService.deleteVacation(vacationId);
    }
}
