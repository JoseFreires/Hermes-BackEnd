package com.hermes.projeto.backend.dto;

import java.time.LocalDate;

public record DadosAtualizacaoPessoaDTO(

        String email,

        String nomeCompleto,

        String telefone,

        LocalDate dataNascimento

) {
}
