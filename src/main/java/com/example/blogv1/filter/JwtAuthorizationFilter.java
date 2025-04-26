package com.example.blogv1.filter;

import com.example.blogv1.Config.ApplicationConstant;
import com.example.blogv1.Config.JwtUtil;
import com.example.blogv1.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("asddsa");
        String jwt = request.getHeader(ApplicationConstant.getJwtHeader());
        if (jwt != null && jwt.startsWith("Bearer "))
            jwt = jwt.substring(7);

        if (jwt != null){
            try{
                String secret = ApplicationConstant.getJwtSecret();
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                response.setHeader(ApplicationConstant.getJwtHeader(),jwt);
                if (secretKey != null){
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey) // Secret key eklenir
                            .build() // JwtParser oluşturulur
                            .parseClaimsJws(jwt) // Token doğrulanır
                            .getBody(); // Token içeriği alınır
                    String username = String.valueOf(claims.get("username"));
                    List<String> authoritiesList = claims.get("authorities", List.class);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,
                            authoritiesList.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException ex) {
                logger.error("JWT expired: {}", ex.getMessage());
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired", request);
                return;
            } catch (JwtException | IllegalArgumentException ex) {
                logger.error("Invalid JWT: {}", ex.getMessage());
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token", request);
                return;
            }catch (NotFoundException ex){
                logger.error("User not found: {}", ex.getMessage());
                writeErrorResponse(response, HttpStatus.NOT_FOUND, "User not found", request);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("req: "+request.getServletPath());
        return request.getServletPath() != null &&  request.getServletPath().startsWith("/api/v1/auth/") || request.getServletPath().startsWith("/api/v1/admin");

    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, HttpServletRequest request) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        String jsonResponse = String.format("""
        {
            "timestamp": "%s",
            "status": %d,
            "error": "%s",
            "message": "%s",
            "path": "%s"
        }
        """,
                java.time.Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
