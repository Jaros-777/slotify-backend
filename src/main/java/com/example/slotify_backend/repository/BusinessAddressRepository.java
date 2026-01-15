package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.BusinessAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAddressRepository extends JpaRepository<BusinessAddress, Long> {
}
