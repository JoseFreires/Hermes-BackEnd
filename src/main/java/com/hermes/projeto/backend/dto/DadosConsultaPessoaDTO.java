package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.security.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DadosConsultaPessoaDTO(

        Long idPessoa,

        String nomeCompleto,

        String cpf,


        String email,


        String telefone,


        LocalDate dataNascimento,

        Boolean ativo
)
{
        public DadosConsultaPessoaDTO(Usuario usuario){
            this(
            usuario.getPessoa().getId(),
            usuario.getPessoa().getNomeCompleto(),
            usuario.getPessoa().getCpf(),
            usuario.getPessoa().getEmail(),
            usuario.getPessoa().getTelefone(),
            usuario.getPessoa().getDataNascimento(),
            usuario.getPessoa().getAtivo()
            );
        }

}
