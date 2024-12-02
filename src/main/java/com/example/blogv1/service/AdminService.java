package com.example.blogv1.service;

import com.example.blogv1.dto.AdminRequestDto;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
    }

    public Admin save(AdminRequestDto adminRequestDto) {
        Admin admin = new Admin(
                adminRequestDto.getFirstName(), adminRequestDto.getLastName(), adminRequestDto.getUsername(), passwordEncoder.encode(adminRequestDto.getPassword())
        );
        return adminRepository.save(admin);
    }
}
