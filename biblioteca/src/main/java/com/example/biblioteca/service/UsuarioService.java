package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.UsuarioRequest;
import com.example.biblioteca.dto.response.UsuarioResponse;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    //CADASTRAR
    @Transactional
    public UsuarioResponse cadastrar(UsuarioRequest request){
        if (repository.existsByEmail(request.email())){
            throw new RuntimeException();
        }

        Usuario novoUsuario = request.toEntity();
        return UsuarioResponse.fromEntity(repository.save(novoUsuario));
    }

    //Listar
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listar(Pageable pageable){
        return repository.findAll(pageable).map(UsuarioResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    //Buscar Por ID
    public UsuarioResponse buscarPorId(Long id){
        return UsuarioResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    //Atualizar
    public UsuarioResponse atualizar(Long id , UsuarioRequest request){
        Usuario usuario = buscarEntidadePorId(id);
        Optional<Usuario> userEmail = repository.findByEmail(request.email());
        if (userEmail.isPresent() && !userEmail.get().getId().equals(usuario.getId())){
            throw new RuntimeException();
        }

        request.preencher(usuario);
        return UsuarioResponse.fromEntity(repository.save(usuario));
    }

    @Transactional
    //Deletar
    public void deletar(Long id){
        Usuario usuario = buscarEntidadePorId(id);
        repository.delete(usuario);
    }



    private Usuario buscarEntidadePorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
