package com.hermes.projeto.backend.dto.response;

import com.hermes.projeto.backend.domain.Moradia;

public record DadosConsultaMoradiaDTO(

        Long idMoradia,

        String numero,

        Long blocoIdBloco,

        String nomeBloco,

        Long condominioId,

        String nomeCondominio
) {
    public DadosConsultaMoradiaDTO(Moradia moradia) {
        this(
                moradia.getId(),
                moradia.getNumero(),
                moradia.getBloco().getId(),
                moradia.getBloco().getNome_torre(),
                moradia.getBloco().getCondominio().getId(),
                moradia.getBloco().getCondominio().getNome_condominio()
        );
    }

}
