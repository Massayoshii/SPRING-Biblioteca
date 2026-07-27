package com.example.biblioteca.dto.request;

import com.example.biblioteca.entity.Autor;
import jakarta.validation.constraints.NotBlank;

public record AutorRequest(
        @NotBlank(message = "nome é obrigatorio")
        String nome,
        @NotBlank(message = "nacionalidade é obrigatorio")
        String nacionalidade
) {
    public void preencher(Autor autor){
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
    }

    public Autor toEntity(){
        Autor autor = new Autor();
        preencher(autor);
        return autor;
    }
}
