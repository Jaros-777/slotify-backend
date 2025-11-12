package com.example.slotify_backend.repository;
import com.example.slotify_backend.entity.BusinessProfile;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    BusinessProfile findByUserId(Long userId);

    Optional<BusinessProfile> findByNameIgnoreCase(@NotBlank String name);
}
