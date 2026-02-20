package com.example.slotify_backend.controller.client;

import com.example.slotify_backend.dto.client.BookedHoursEventDTO;
import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.dto.client.OrderResponseDTO;
import com.example.slotify_backend.dto.client.PreOrderResponseDTO;
import com.example.slotify_backend.service.client.BusinessOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Order", description = "Order reservation operations")
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class BussinessOrderController {
    private final BusinessOrderService businessOrderService;

    @GetMapping("{id}")
    public OrderDTO getAllServicesByUser(@PathVariable("id") Long serviceId){
        return businessOrderService.getAllDetailsByServiceId(serviceId);
    }

    @PostMapping("/pre")
    @ResponseStatus(HttpStatus.CREATED)
    public String preOrder( @Valid @RequestBody PreOrderResponseDTO orderDTO) {
        return businessOrderService.preOrder(orderDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertOrder( @Valid @RequestBody OrderResponseDTO orderDTO) {
        businessOrderService.insertOrder(orderDTO);
    }

    @GetMapping("/booked/{userId}/{chosenDay}")
    public List<BookedHoursEventDTO> getBookedHoursEvents( @PathVariable("userId") Long userId, @PathVariable("chosenDay") LocalDateTime chosenDay){
       return businessOrderService.getBookedHoursEvents(userId,chosenDay);
    }
}
