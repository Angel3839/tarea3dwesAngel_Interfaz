package com.Angelvf3839.tarea3dwesangel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/personal/**").hasRole("USER")
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/index")
                .defaultSuccessUrl("/personal")
                .permitAll()
            )
            .logout(logout -> logout.logoutUrl("/logout").permitAll());

        return http.build();
    }
}
