package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.EmprestimoRequest;
import com.example.biblioteca.dto.response.EmprestimoResponse;
import com.example.biblioteca.service.EmprestimoService;
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
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService service;

    @PostMapping
    public ResponseEntity<EmprestimoResponse> realizarEmprestimo(@RequestBody @Valid EmprestimoRequest request){
        EmprestimoResponse response = service.emprestar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<EmprestimoResponse> listarEmprestimos(@PageableDefault Pageable pageable){
        return service.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> buscarEmprestimoPorId(@PathVariable Long id){
        EmprestimoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    public List<EmprestimoResponse> listarEmprestimosAtivos(){
        return service.listarAtivos();
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoResponse> devolverEmprestimo(@PathVariable Long id){
        EmprestimoResponse response = service.devolver(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
