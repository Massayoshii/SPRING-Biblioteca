package com.example.biblioteca.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET , "/autores/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT , "/autores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE , "/autores/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET , "/livros/**").authenticated()
                        .requestMatchers(HttpMethod.POST , "/livros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT , "/livros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE , "/livros/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET , "/emprestimos/**").authenticated()
                        .requestMatchers(HttpMethod.POST ,"/emprestimos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/emprestimos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE , "/emprestimos/**").hasRole("ADMIN")

                        .anyRequest().permitAll())

                .build();
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }
}
