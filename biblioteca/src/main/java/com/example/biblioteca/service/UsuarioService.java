package com.example.biblioteca.service;

import com.example.biblioteca.config.TokenService;
import com.example.biblioteca.dto.request.UsuarioLoginRequest;
import com.example.biblioteca.dto.request.UsuarioRequest;
import com.example.biblioteca.dto.response.UsuarioLoginResponse;
import com.example.biblioteca.dto.response.UsuarioResponse;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.exception.EmailAlreadyExistsException;
import com.example.biblioteca.exception.ResourceNotFoundException;
import com.example.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @Transactional
    public UsuarioLoginResponse login(UsuarioLoginRequest request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email() , request.senha());
        Authentication authentication = manager.authenticate(userAndPass);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.generateToken(usuario);

        return new UsuarioLoginResponse(token);
    }

    //CADASTRAR
    @Transactional
    public UsuarioResponse cadastrar(UsuarioRequest request){
        if (repository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException("Usuario ja cadastrado com o email "+ request.email());
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
            throw new EmailAlreadyExistsException("Usuario ja cadastrado com o email "+ request.email());
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com o id " + id));
    }
}
