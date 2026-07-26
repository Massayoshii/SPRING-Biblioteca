package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro , Long> {
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByAutor(Autor autor);
}
