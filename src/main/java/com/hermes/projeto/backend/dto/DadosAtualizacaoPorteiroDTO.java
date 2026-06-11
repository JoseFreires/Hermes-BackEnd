package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.enums.TurnoPorteiro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DadosAtualizacaoPorteiroDTO(


        @NotNull(message = "O turno é obrigatório")
        TurnoPorteiro turno,

        @NotBlank(message = "A empresa responsável é obrigatória")
        @Size(max = 100, message = "A empresa responsável não pode ter mais de 100 caracteres")
        String empresaResponsavel,

        String nomeCompleto,

        String telefone,

        LocalDate dataNascimento
) {
}
