package com.hermes.projeto.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoMoradiaDTO(

        @NotNull(message = "O ID da moradia é obrigatório")
        Long idMoradia,

        @NotBlank(message = "O número da moradia é obrigatório")
        @Size(max = 20, message = "O número da moradia não pode ter mais de 20 caracteres")
        String numero,

        @NotNull(message = "O ID do bloco é obrigatório")
        Long blocoIdBloco
) {
}
