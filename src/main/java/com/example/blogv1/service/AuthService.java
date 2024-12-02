package com.example.blogv1.service;

import com.example.blogv1.Config.ApplicationConstant;
import com.example.blogv1.dto.AuthRequest;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.exception.NotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<String> authentication(AuthRequest authRequest) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.getUsername(),authRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);

        if (authenticate.isAuthenticated()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(ApplicationConstant.getJwtHeader(),generateJwt(authenticate))
                    .body("Success Login");
        }else
            throw new NotFoundException("Wrong information");
    }

    public String generateJwt(Authentication authenticate){
        String secret = ApplicationConstant.getJwtSecret();
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer("Blog")
                .setSubject("Jwt")
                .claim("username", authenticate.getName())
                .claim("authorities",authenticate.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 300000000))  // 80 saat civarı
                .signWith(secretKey).compact();// compact methodu str değeri olarak çıkartmayı sağlar.

    }
}
