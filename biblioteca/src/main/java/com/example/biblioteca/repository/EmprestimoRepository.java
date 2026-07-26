package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Emprestimo;
import com.example.biblioteca.entity.Livro;
import com.example.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo , Long> {
    List<Emprestimo> findByUsuario(Usuario usuario);

    List<Emprestimo> findByLivro(Livro livro);

}
