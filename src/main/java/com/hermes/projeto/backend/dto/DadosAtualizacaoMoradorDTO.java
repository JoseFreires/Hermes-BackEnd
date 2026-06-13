package com.hermes.projeto.backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

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
