package com.example.blogv1.controller;

import com.example.blogv1.dto.AdminRequestDto;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.service.AdminService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<Admin> save(@RequestBody AdminRequestDto admin) {
        return new ResponseEntity<>(adminService.save(admin), HttpStatus.CREATED);
    }
}
