package com.himmerland.hero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Slå CSRF fra (vi bruger ikke formular-login lige nu)
            .csrf(csrf -> csrf.disable())
            // ALLE requests er tilladt uden login
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        // Byg og returnér sikkerhedskæden
        return http.build();
    }
}
