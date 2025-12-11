package com.example.slotify_backend.service.client;

import com.example.slotify_backend.dto.client.OrderDTO;
import com.example.slotify_backend.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BussinessOrderService {
    private final OrderMapper orderMapper;

    public OrderDTO getAllDetailsByServiceId(Long serviceId) {

        return orderMapper.toDTO(serviceId);
    }
}
