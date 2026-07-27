package com.example.biblioteca.dto.response;

import com.example.biblioteca.entity.Livro;

public record LivroResponse(
        Long id,
        String titulo,
        String isbn,
        Integer anoPublicacao,
        Integer quantidade,
        String autor
) {
    public static LivroResponse fromEntity(Livro livro){
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getQuantidade(),
                livro.getAutor().getNome()
        );
    }
}
