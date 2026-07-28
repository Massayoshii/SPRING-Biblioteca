package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.LivroRequest;
import com.example.biblioteca.dto.response.LivroResponse;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrarLivro(@RequestBody @Valid LivroRequest request){
        LivroResponse response = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<LivroResponse> listarLivros(@PageableDefault(size = 5 , sort = "titulo") Pageable pageable){
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarLivroPorId(@PathVariable Long id){
        LivroResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/titulo")
    public List<LivroResponse> buscarLivroPorTitulo(@RequestParam String titulo){
        return service.buscarPorTitulo(titulo);
    }

    @GetMapping("/autor/{autorId}")
    public List<LivroResponse> buscarLivroPorAutor(@PathVariable Long autorId){
        return service.buscarPorAutor(autorId);
    }

    @GetMapping("/{id}/disponibilidade")
    public ResponseEntity<LivroResponse> consultarLivro(@PathVariable Long id){
        LivroResponse response = service.consultarDisponibilidade(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizarLivro(@PathVariable Long id,
                                                        @RequestBody @Valid LivroRequest request){
        LivroResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
