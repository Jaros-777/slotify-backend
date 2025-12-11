package com.example.slotify_backend.controller.client;

import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.service.client.BussinessOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class BussinessOrderController {
    private final BussinessOrderService bussinessOrderService;

    @GetMapping("{id}")
    public OrderDTO getAllServicesByUser(@PathVariable("id") Long serviceId){
        return bussinessOrderService.getAllDetailsByServiceId(serviceId);
    };
}
