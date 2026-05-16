package com.sortcery.backend.config;

import java.time.Instant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.sortcery.backend.model.User;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .authorizeHttpRequests(request -> {
                // TODO: Proper auth when implemented
                request.requestMatchers("/api/**").authenticated();
                // request.requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/logout").permitAll();
                request.anyRequest().hasAnyRole(User.Role.ADMIN.toString());
            })
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint((request, response, exception) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write(
                                "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"" + exception.getMessage() + "\",\"path\":\""
                                + request.getRequestURI() + "\"," + "\"timestamp\":\"" + Instant.now() + "\"}"
                        );
                    })
                    .accessDeniedHandler((request, response, exception) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.getWriter().write(
                                "{\"status\":403,\"error\":\"Forbidden\",\"message\":\"" + exception.getMessage() + "\",\"path\":\""
                                + request.getRequestURI() + "\"," + "\"timestamp\":\"" + Instant.now() + "\"}"
                        );
                    })
                    );

            return http.build();
    }
}
