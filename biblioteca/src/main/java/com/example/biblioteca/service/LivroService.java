package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.LivroRequest;
import com.example.biblioteca.dto.response.LivroResponse;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Livro;
import com.example.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    //CADASTRAR
    @Transactional
    public LivroResponse cadastrar(LivroRequest request){
        if (repository.existsByIsbn(request.isbn())){
            throw new RuntimeException();
        }

        Livro livro = request.toEntity();
        return LivroResponse.fromEntity(repository.save(livro));
    }

    //LISTAR
    @Transactional(readOnly = true)
    public Page<LivroResponse> listar(Pageable pageable){
        return repository.findAll(pageable).map(LivroResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    //BUSCAR POR ID
    public LivroResponse buscarPorId(Long id){
        return LivroResponse.fromEntity(buscarEntidadePorId(id));
    }

    //BUSCAR POR TITULO
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorTitulo(String titulo){
        return repository.findByTitulo(titulo);
    }


    //BUSCAR POR AUTOR
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorAutor(Autor autor){
        return repository.findByAutor(autor);
    }

    //ATUALIZAR
    @Transactional
    public LivroResponse atualizar(Long id , LivroRequest request){
        Livro livro = buscarEntidadePorId(id);
        Optional<Livro> livroIsbn = repository.findByIsbn(request.isbn());

        if (livroIsbn.isPresent() && !livroIsbn.get().getId().equals(livro.getId())){
            throw new RuntimeException();
        }

        request.preencher(livro);
        return LivroResponse.fromEntity(repository.save(livro));
    }

    //DELETAR
    @Transactional
    public void deletar(Long id){
        Livro livro = buscarEntidadePorId(id);
        repository.delete(livro);
    }

    //CONSULTAR DISPONIBILIDADE
    @Transactional(readOnly = true)
    public LivroResponse consultarDisponibilidade(Long id){
        Livro livro = buscarEntidadePorId(id);
        if (livro.getQuantidade() == 0){
            throw new RuntimeException();
        }

        return LivroResponse.fromEntity(livro);

    }




    private Livro buscarEntidadePorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
