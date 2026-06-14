package com.hermes.projeto.backend.dto.request;

import java.time.LocalDate;

public record DadosAtualizacaoMoradorDTO(

        LocalDate dataChegada,

        LocalDate dataSaida,

        String fotoPerfil,


        Long moradiaIdMoradia,

        String nomeCompleto,

        String telefone,

        LocalDate dataNascimento
) {
}
