package com.example.biblioteca.config;

import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            Optional<JWTUserData> optUser = tokenService.validateToken(token);

            if (optUser.isPresent()){
                JWTUserData userData = optUser.get();
                Optional<Usuario> usuario = usuarioRepository.findById(optUser.get().userId());

                if (usuario.isPresent()){
                    UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(userData.userId() , userData.email());
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(userAndPass);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
