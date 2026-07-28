package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.EmprestimoRequest;
import com.example.biblioteca.dto.response.EmprestimoResponse;
import com.example.biblioteca.entity.Emprestimo;
import com.example.biblioteca.entity.Livro;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.exception.BusinessException;
import com.example.biblioteca.exception.ResourceNotFoundException;
import com.example.biblioteca.repository.EmprestimoRepository;
import com.example.biblioteca.repository.LivroRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    @Transactional
    public EmprestimoResponse emprestar(EmprestimoRequest request){
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com o id " + request.usuarioId()));

        Livro livro = livroRepository.findById(request.livroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado com o id " + request.livroId()));

        if (livro.getQuantidade() <= 0 ){
            throw new BusinessException("Livro indisponível para empréstimo");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDateTime.now());
        livro.setQuantidade(livro.getQuantidade() -1);
        emprestimo.setDevolucao(false);

        return EmprestimoResponse.fromEntity(emprestimoRepository.save(emprestimo));
    }

    @Transactional
    public EmprestimoResponse devolver(Long id){
        Emprestimo emprestimo = buscarEntidadePorId(id);

        if (emprestimo.isDevolucao()){
            throw new BusinessException("Livro ja foi devolvido");
        }

        Livro livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() + 1);

        emprestimo.setDevolucao(true);
        emprestimo.setDataDevolucao(LocalDateTime.now());


        return EmprestimoResponse.fromEntity(emprestimoRepository.save(emprestimo));
    }

    @Transactional(readOnly = true)
    public Page<EmprestimoResponse> listarTodos(Pageable pageable){
        return emprestimoRepository.findAll(pageable)
                .map(EmprestimoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public EmprestimoResponse buscarPorId(Long id){
        Emprestimo emprestimo = buscarEntidadePorId(id);
        return EmprestimoResponse.fromEntity(emprestimo);
    }

    @Transactional(readOnly = true)
    public List<EmprestimoResponse> listarAtivos(){
        return emprestimoRepository.findAll()
                .stream()
                .filter(e -> !e.isDevolucao())
                .map(EmprestimoResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void excluir(Long id){
        Emprestimo emprestimo = buscarEntidadePorId(id);
        if (!emprestimo.isDevolucao()){
            throw new BusinessException("Livro nao foi devolvido no momento");
        }
        emprestimoRepository.delete(emprestimo);
    }


    private Emprestimo buscarEntidadePorId(Long id){
        return emprestimoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Emprestimo nao encontrado com o id " + id));
    }
}
