package com.example.slotify_backend.repository;
import com.example.slotify_backend.entity.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    BusinessProfile findByUserId(Long userId);
}
