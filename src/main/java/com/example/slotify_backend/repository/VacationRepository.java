package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findAllByUserId(Long userId);
}
