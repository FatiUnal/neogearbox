package com.example.blogv1.repository;

import com.example.blogv1.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}
