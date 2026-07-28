package com.example.biblioteca.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.biblioteca.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        Algorithm algorithml = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("userId" , usuario.getId())
                .withSubject(usuario.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithml);
    }

    public Optional<JWTUserData> validateToken(String token){
        try {
            Algorithm algorithml = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithml).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .build());
        }
        catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
