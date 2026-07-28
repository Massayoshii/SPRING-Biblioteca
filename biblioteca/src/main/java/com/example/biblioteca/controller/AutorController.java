package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.AutorRequest;
import com.example.biblioteca.dto.response.AutorResponse;
import com.example.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    @PostMapping
    public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody @Valid AutorRequest request){
        AutorResponse response = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<AutorResponse> listarAutores(@PageableDefault(size = 5 , sort = "nome") Pageable pageable){
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> buscarAutorPorId(@PathVariable Long id){
        AutorResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable Long id ,
                                                        @RequestBody @Valid AutorRequest request){
        AutorResponse response = service.atualizar(id , request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
