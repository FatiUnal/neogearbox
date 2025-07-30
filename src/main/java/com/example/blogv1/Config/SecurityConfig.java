package com.example.blogv1.Config;

import com.example.blogv1.filter.JwtAuthorizationFilter;
import com.example.blogv1.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Value("${spring.cors.url}")
    private String url;
    @Value("${spring.cors.url.www}")
    private String urlWww;
    public SecurityConfig(CustomUserDetailService customUserDetailService, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.customUserDetailService = customUserDetailService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x->x
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/admin").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/post").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/post").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/get-by-id").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/get-ilan").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/v1/post").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/post/status").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/post/order").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/main").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/small").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/status").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/estate").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/category").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/post/category/name").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/search").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/post/searcheng").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/image").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/image/cover").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/image/cover/{postId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/image/{postID}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/image/get-by-id").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/image/cover-delete").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/image/delete-by-id").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/image/post").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/upload/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/image/category").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/image/category").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/image/pdf").hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/swagger-ui/index.html","/swagger-ui/**","/v3/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/v1/category").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/category/get-by-id").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/category").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/category").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/category/get-by-link-name").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/category").hasAuthority("ROLE_ADMIN")


                        .requestMatchers("/error/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(x-> x.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration ccfg = new CorsConfiguration();
                ccfg.setAllowedOrigins(List.of(url,urlWww, "http://localhost","http://localhost:5173"));
                ccfg.setAllowedMethods(Collections.singletonList("*"));
                ccfg.setAllowCredentials(true);
                ccfg.setAllowedHeaders(Collections.singletonList("*"));
                ccfg.setExposedHeaders(List.of("Authorization"));
                ccfg.setMaxAge(3600L);
                return ccfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
