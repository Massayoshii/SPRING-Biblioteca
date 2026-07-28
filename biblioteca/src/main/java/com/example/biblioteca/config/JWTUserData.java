package com.example.biblioteca.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId , String email) {
}
