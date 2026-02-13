package com.example.slotify_backend.mapper;

import com.example.slotify_backend.dto.company.VacationDTO;
import com.example.slotify_backend.entity.Event;
import com.example.slotify_backend.entity.User;
import com.example.slotify_backend.entity.Vacation;
import com.example.slotify_backend.entity.enums.BookingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class VacationMapper {

    public VacationDTO toDTO(Vacation vacation, List<Long> eventsList) {
        return new VacationDTO(
                vacation.getId(),
                vacation.getTitle(),
                vacation.getStartDate(),
                vacation.getEndDate(),
                eventsList
        );
    }

    public List<Event> allToEventEntity(User user, Vacation vacation) {
        List<Event> events = new ArrayList<>();
        long days = ChronoUnit.DAYS.between(vacation.getStartDate(), vacation.getEndDate());

        for(int i = 0; i <= days; i++){
            LocalDateTime startDate = vacation.getStartDate().plusDays(i);
            LocalDateTime endDate = startDate.withHour(vacation.getEndDate().getHour()).withMinute(vacation.getEndDate().getMinute());
            events.add(new Event(
                    user,
                    startDate,
                    endDate,
                    vacation.getTitle(),
                    BookingStatus.VACATION,
                    vacation
            ));
        }
        return events;
    }


}
