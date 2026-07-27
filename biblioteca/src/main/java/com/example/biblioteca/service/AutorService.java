package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.AutorRequest;
import com.example.biblioteca.dto.response.AutorResponse;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;

    @Transactional
    public AutorResponse cadastrar(AutorRequest request){
        Autor novoAutor = request.toEntity();
        return AutorResponse.fromEntity(repository.save(novoAutor));
    }

    @Transactional(readOnly = true)
    public Page<AutorResponse> listar(Pageable pageable){
        return repository.findAll(pageable).map(AutorResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public AutorResponse buscarPorId(Long id){
        return AutorResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public AutorResponse atualizar(Long id,AutorRequest request){
        Autor autorAtualizado = buscarEntidadePorId(id);
        request.preencher(autorAtualizado);
        return AutorResponse.fromEntity(autorAtualizado);
    }

    @Transactional
    public void deletar(Long id){
        Autor autor = buscarEntidadePorId(id);
        repository.delete(autor);
    }






    private Autor buscarEntidadePorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
