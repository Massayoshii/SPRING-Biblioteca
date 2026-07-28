package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.UsuarioRequest;
import com.example.biblioteca.dto.response.UsuarioResponse;
import com.example.biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService service;


    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> registar(@RequestBody UsuarioRequest request) {
        UsuarioResponse response = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

