package com.example.blogv1.service;

import com.example.blogv1.dto.AdminRequestDto;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.AdminRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
    }

    public Admin save(AdminRequestDto adminRequestDto) {
        // Zorunlu alanların boş olup olmadığını kontrol et
        if (adminRequestDto.getFirstName() == null || adminRequestDto.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (adminRequestDto.getLastName() == null || adminRequestDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (adminRequestDto.getUsername() == null || adminRequestDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (adminRequestDto.getPassword() == null || adminRequestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        // Kullanıcı adı benzersiz mi?
        if (adminRepository.existsByUsername(adminRequestDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        // Şifre karmaşıklığını kontrol et
        if (!isPasswordStrong(adminRequestDto.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain a mix of letters, numbers, and special characters");
        }

        Admin admin = new Admin(
                adminRequestDto.getFirstName(),
                adminRequestDto.getLastName(),
                adminRequestDto.getUsername(),
                adminRequestDto.getPassword()
        );
        return adminRepository.save(admin);
    }

    private boolean isPasswordStrong(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
}
