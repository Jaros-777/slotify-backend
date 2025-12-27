package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Boolean existsByEmail(String email);
    Client findByUserAccountId(Long userId);
}
