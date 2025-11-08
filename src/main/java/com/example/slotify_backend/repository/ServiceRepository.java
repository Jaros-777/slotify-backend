package com.example.slotify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.slotify_backend.entity.ServiceEntity;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findAllByUserId(Long userId);
    ServiceEntity findByIsEditableAndUserId(Boolean isEditable, Long userId);
}
