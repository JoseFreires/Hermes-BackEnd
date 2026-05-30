package com.hermes.projeto.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record DadosRegistrarMoradorDTO(

        @NotNull @Valid
        DadosPessoaDTO pessoa,

        @NotNull @Valid
        DadosLoginDTO usuario,

        @NotNull(message = "O ID da moradia/apartamento é obrigatório")
        Long idMoradia,

        @NotBlank(message = "A foto de perfil é obrigatória")
        String fotoPerfil,

        @NotNull(message = "A data de chegada ao condomínio é obrigatória")
        LocalDate dataChegada
) {
}