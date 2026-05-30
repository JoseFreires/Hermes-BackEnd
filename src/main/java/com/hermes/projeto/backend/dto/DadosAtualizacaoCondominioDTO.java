package com.hermes.projeto.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoCondominioDTO(

        @NotNull(message = "O ID do condomínio é obrigatório")
        Long idCondominio,

        @NotBlank(message = "O nome do condomínio é obrigatório")
        @Size(max = 120, message = "O nome do condomínio não pode ter mais de 120 caracteres")
        String nomeCondominio
) {
}
