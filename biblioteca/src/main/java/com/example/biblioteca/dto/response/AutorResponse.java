package com.example.biblioteca.dto.response;

import com.example.biblioteca.entity.Autor;

public record AutorResponse(
        Long id,
        String nome,
        String nacionalidade
) {
    public static AutorResponse fromEntity(Autor autor){
        return new AutorResponse(
                autor.getId(),
                autor.getNome(),
                autor.getNacionalidade()
        );
    }
}
