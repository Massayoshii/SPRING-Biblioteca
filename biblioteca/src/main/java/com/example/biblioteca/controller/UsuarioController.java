package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.UsuarioRequest;
import com.example.biblioteca.dto.response.UsuarioResponse;
import com.example.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public Page<UsuarioResponse> listarUsuarios(@PageableDefault(size = 5 , sort = "nome") Pageable pageable){
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable Long id){
        UsuarioResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id,
                                                            @RequestBody @Valid UsuarioRequest request){
        UsuarioResponse response = service.atualizar(id , request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
