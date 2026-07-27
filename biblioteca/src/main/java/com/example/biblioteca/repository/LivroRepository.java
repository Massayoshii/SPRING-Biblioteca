package com.example.biblioteca.repository;

import com.example.biblioteca.dto.response.LivroResponse;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro , Long> {
    List<LivroResponse> findByTitulo(String titulo);
    List<LivroResponse> findByAutor(Autor autor);

    boolean existsByIsbn(@NotBlank(message = "isbn é obrigatorio") @Size(min = 10 , max = 13) String isbn);

    Optional<Livro> findByIsbn(@NotBlank(message = "isbn é obrigatorio") @Size(min = 10 , max = 13) String isbn);
}
