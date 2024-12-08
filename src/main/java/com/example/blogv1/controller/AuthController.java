package com.example.blogv1.controller;

import com.example.blogv1.Config.ApplicationConstant;
import com.example.blogv1.Config.JwtUtil;
import com.example.blogv1.dto.AuthRequest;
import com.example.blogv1.dto.LoginRes;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.service.AdminService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;



    public AuthController(AuthenticationManager authenticationManager, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;


    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest loginReq)  {

        try {
            Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginReq.getUsername(),loginReq.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);

            String username = authentication.getName();
            Admin admin = adminService.getByUsername(username);
            System.out.println(admin.getId());
            String token = generateJwt(authenticate);
            System.out.println(token);
            LoginRes loginRes = new LoginRes(username,token);
            System.out.println(loginRes.getToken());

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("İnvalid username or password");
        }
    }

    public String generateJwt(Authentication authenticate){
        String secret = ApplicationConstant.getJwtSecret();
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer("OneUserOneAdmin")
                .setSubject("Jwt Token")
                .claim("username", authenticate.getName())
                .claim("authorities",authenticate.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 300000000))  // 80 saat civarı
                .signWith(secretKey).compact();// compact methodu str değeri olarak çıkartmayı sağlar.

    }
}
