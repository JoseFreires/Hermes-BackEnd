package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.enums.TurnoPorteiro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DadosAtualizacaoPorteiroDTO(

        TurnoPorteiro turno,

        String empresaResponsavel,

        String nomeCompleto,

        String telefone,

        LocalDate dataNascimento,

        DadosRegistrarPessoaDTO pessoa,

        DadosLoginDTO usuario
) {
}
