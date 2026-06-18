package com.hermes.projeto.backend.dto.request;

import java.time.LocalDate;

public record DadosAtualizacaoPessoaDTO(

        String email,

        String nomeCompleto,

        String telefone,

        LocalDate dataNascimento

) {
}
