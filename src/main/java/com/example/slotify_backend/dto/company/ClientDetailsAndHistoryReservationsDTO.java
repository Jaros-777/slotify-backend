package com.example.slotify_backend.dto.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDetailsAndHistoryReservationsDTO {
    List<ClientHistoryReservationDTO> historyDTO;

    public ClientDetailsAndHistoryReservationsDTO(Integer clientPhone, String clientEmail, String clientName, Long clientId, List<ClientHistoryReservationDTO> historyDTO) {
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.clientName = clientName;
        this.clientId = clientId;
        this.historyDTO = historyDTO;
    }

    Long clientId;
    String clientName;
    String clientEmail;
    Integer clientPhone;


}
