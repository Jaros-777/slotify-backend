package com.example.slotify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.slotify_backend.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
