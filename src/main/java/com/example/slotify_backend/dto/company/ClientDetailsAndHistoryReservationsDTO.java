package com.example.slotify_backend.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "Representation of client details and history reservation response")
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
