package com.example.slotify_backend.controller.client;

import com.example.slotify_backend.dto.client.BookedHoursEventDTO;
import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.dto.client.OrderResponseDTO;
import com.example.slotify_backend.service.client.BussinessOrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class BussinessOrderController {
    private final BussinessOrderService bussinessOrderService;

    @GetMapping("{id}")
    public OrderDTO getAllServicesByUser(@PathVariable("id") Long serviceId){
        return bussinessOrderService.getAllDetailsByServiceId(serviceId);
    };

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertOrder( @Valid @RequestBody OrderResponseDTO orderDTO) {
        bussinessOrderService.insertOrder(orderDTO);
    }

    @GetMapping("/booked/{serviceId}/{chosenDay}")
    public List<BookedHoursEventDTO> getBookedHoursEvents(@PathVariable("serviceId") Long serivceId, @PathVariable("chosenDay") LocalDateTime chosenDay){
       return bussinessOrderService.getBookedHoursEvents(serivceId,chosenDay);
    }
}
