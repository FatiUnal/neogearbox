package com.example.blogv1.service;

import com.example.blogv1.entity.Admin;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public CustomUserDetailService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(username));
        return new User(admin.getUsername(), admin.getPassword(), List.of(admin.getRole()));
    }
}
