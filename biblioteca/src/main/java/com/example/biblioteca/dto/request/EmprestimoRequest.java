package com.example.biblioteca.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmprestimoRequest(
        @NotNull(message = "Livro é obrigatorio")
        Long livroId,
        Long usuarioId
) {
}
