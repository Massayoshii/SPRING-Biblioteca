package com.example.biblioteca.dto.request;

import com.example.biblioteca.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank(message = "nome é obrigatorio")
        String nome ,
        @NotBlank(message = "email é obrigatorio")
        @Email
        String email,
        @NotBlank(message = "senha é obrigatorio")
        @Size(min = 4 ,  message = "senha deve conter mais de 4 caracteres")
        String senha
) {
    public void preencher(Usuario usuario){
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
    }

    public Usuario toEntity(){
        Usuario usuario = new Usuario();
        preencher(usuario);
        return usuario;
    }
}
