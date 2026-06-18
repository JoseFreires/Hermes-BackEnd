package com.hermes.projeto.backend.dto.response;

import com.hermes.projeto.backend.domain.Moradia;

public record DadosConsultaMoradiaResumoDTO (

        Long idMoradia,

        String numero
) {
    public DadosConsultaMoradiaResumoDTO(Moradia moradia) {
        this(
                moradia.getId(),
                moradia.getNumero()
        );
    }

}

