package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.company.VacationDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.Vacation;
import com.example.slotify_backend.entity.enums.BookingStatus;
import com.example.slotify_backend.entity.enums.EventType;
import com.example.slotify_backend.mapper.EventMapper;
import com.example.slotify_backend.mapper.VacationMapper;
import com.example.slotify_backend.repository.EventRepository;
import com.example.slotify_backend.repository.UserRepository;
import com.example.slotify_backend.repository.VacationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VacationService {
    private final JwtService jwtService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;
    private final VacationMapper vacationMapper;

    public List<VacationDTO> getVacationList(String authHeader) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);

        List<VacationDTO> vacationDTOList = new ArrayList<>();
        List<Vacation> vacations = vacationRepository.findAllByUserId(userId);
        vacations.forEach(vacation -> {
            List<Event> eventsList = eventRepository.findAllByVacation(vacation);
            List<Long> eventsIdList = eventsList.stream().map(Event::getId).collect(Collectors.toList());
            vacationDTOList.add(vacationMapper.toDTO(vacation, eventsIdList));
        });
        return vacationDTOList;
    }

    @Transactional
    public void createUpdateVacation(String authHeader, VacationDTO vacationDTO) {
        Long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (vacationDTO.id() != null) {
            vacationRepository.findById(vacationDTO.id()).ifPresent(vacation -> {
                eventRepository.deleteAll(eventRepository.findAllByVacation(vacation));
                vacationRepository.delete(vacation);
            });
        }
        Vacation vacation = new Vacation(vacationDTO.name(), vacationDTO.startDate(), vacationDTO.endDate(), user);
        vacationRepository.save(vacation);
        eventRepository.saveAll(vacationMapper.allToEventEntity(user, vacation));


    }


    public void deleteVacation(Long vacationId) {
        vacationRepository.findById(vacationId).ifPresent(vacation -> {
            List<Event> events = eventRepository.findAllByVacation(vacation);

            eventRepository.deleteAll(events);
        });
        vacationRepository.deleteById(vacationId);
    }
}
