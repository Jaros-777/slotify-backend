package com.example.slotify_backend.repository;

import com.example.slotify_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Boolean existsByEmail(String email);
}
