package com.example.blogv1.Config;

import com.example.blogv1.filter.JwtValidationFilter;
import com.example.blogv1.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService userDetailsService;
    private final JwtValidationFilter jwtValidationFilter;

    public SecurityConfig(CustomUserDetailService userDetailsService, JwtValidationFilter jwtValidationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtValidationFilter = jwtValidationFilter;
    }

    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // CSRF korumasını devre dışı bırak
                .csrf(AbstractHttpConfigurer::disable)

                // Stateless oturum yönetimini ayarla
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // İzin verilen isteklere erişimi yapılandır
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll() // Sadece POST isteği için izin ver
                        .requestMatchers("/error").permitAll() // /error endpoint'ine herkesin erişimine izin ver
                        .anyRequest().permitAll()) // Diğer tüm isteklerin kimlik doğrulama gerektirmesini sağla

                // CORS ayarlarını yap
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // JWT veya diğer token tabanlı kimlik doğrulama yapılandırmalarını entegre et
                .formLogin(AbstractHttpConfigurer::disable) // Form tabanlı login'i devre dışı bırak, çünkü JWT kullanıyorsanız bunu istemezsiniz.
                .httpBasic(AbstractHttpConfigurer::disable) // Temel HTTP kimlik doğrulamasını devre dışı bırak
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));  // Frontend adresi
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider kullanıyoruz
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())  // Burada AuthenticationProvider'ı ekliyoruz
                .build();
    }

}
