package com.hermes.projeto.backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMoradorDTO(

        @NotNull(message = "O ID do morador é obrigatório")
        Long idMorador,

        @NotNull(message = "A data de chegada é obrigatória")
        LocalDate dataChegada,

        LocalDate dataSaida,

        String fotoPerfil,

        @NotNull(message = "O ID da pessoa é obrigatório")
        Long pessoaIdPessoa,

        @NotNull(message = "O ID da moradia é obrigatório")
        Long moradiaIdMoradia
) {
}
