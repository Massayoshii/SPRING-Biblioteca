package com.example.biblioteca.dto.response;

import com.example.biblioteca.entity.Emprestimo;

import java.time.LocalDateTime;

public record EmprestimoResponse(
        Long id,
        String usuario,
        String livro,
        LocalDateTime dataEmprestimo,
        LocalDateTime dataDevolucao,
        boolean devolucao
) {
    public static EmprestimoResponse fromEntity(Emprestimo emprestimo){
        return new EmprestimoResponse(
                emprestimo.getId(),
                emprestimo.getUsuario().getNome(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.isDevolucao()
        );
    }
}
