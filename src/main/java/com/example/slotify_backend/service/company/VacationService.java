package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.VacationDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VacationService {
    private final JwtService jwtService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    public List<VacationDTO> getVacationList(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);

        List<Event> events = eventRepository.findAllByUserIdAndBookingStatus(userId, BookingStatus.VACATION);
        List<VacationDTO> vacationDTOList = new ArrayList<>();
        events.forEach(event -> {
            vacationDTOList.add(eventMapper.toVacationDTO(event));
        });
        return vacationDTOList;
    }

    @Transactional
    public void createUpdateVacation(String authHeader,VacationDTO vacationDTO) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtService.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        System.out.println(vacationDTO.id());
        if(vacationDTO.id() != null){
            System.out.println("Update");
            eventRepository.findById(vacationDTO.id()).ifPresent(event -> {
                event.setStartDate(vacationDTO.startDate());
                event.setEndDate(vacationDTO.endDate());
                event.setDescription(vacationDTO.name());
            });
        }else{
            System.out.println("New");
            eventRepository.save(eventMapper.toEntity(vacationDTO,user));
        }

    };

    public void deleteVacation(Long vacationId) {
        eventRepository.deleteById(vacationId);
    }
}
