package com.example.shortening.repository;

import com.example.shortening.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserEntity, Long> {
    public Optional<UserEntity> findById(Long id);
    public Optional<UserEntity> findByUsername(String username);
}








