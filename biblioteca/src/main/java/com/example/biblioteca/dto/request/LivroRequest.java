package com.example.biblioteca.dto.request;

import com.example.biblioteca.entity.Livro;
import jakarta.validation.constraints.*;

public record LivroRequest(
        @NotBlank(message = "titulo é obrigatorio")
        String titulo,
        @NotBlank(message = "isbn é obrigatorio")
        @Size(min = 10 , max = 13)
        String isbn ,
        @NotNull(message = "ano de publicacao é obrigatorio")
        @Positive
        Integer anoPublicado,
        @NotNull(message = "quantidade é obrigatorio")
        @PositiveOrZero
        Integer quantidade,
        @NotNull
        Long autorId
) {

    public void preencher(Livro livro){
        livro.setTitulo(titulo);
        livro.setIsbn(isbn);
        livro.setAnoPublicacao(anoPublicado);
        livro.setQuantidade(quantidade);
    }

    public Livro toEntity(){
        Livro livro = new Livro();
        preencher(livro);
        return livro;
    }
}
