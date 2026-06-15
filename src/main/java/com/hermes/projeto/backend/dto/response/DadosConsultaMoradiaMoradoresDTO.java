package com.hermes.projeto.backend.dto.response;

import com.hermes.projeto.backend.domain.Moradia;
import com.hermes.projeto.backend.domain.Morador;

import java.util.List;

public record DadosConsultaMoradiaMoradoresDTO (

        Long idMoradia,

        String numero,

        Long blocoIdBloco,

        String nomeBloco,

        Long condominioId,

        String nomeCondominio,

        List<DadosConsultaMoradorResumoDTO> moradores

){
    public DadosConsultaMoradiaMoradoresDTO(Moradia moradia) {
        this(
                moradia.getId(),
                moradia.getNumero(),
                moradia.getBloco().getId(),
                moradia.getBloco().getNome_torre(),
                moradia.getBloco().getCondominio().getId(),
                moradia.getBloco().getCondominio().getNome_condominio(),
                moradia.getMoradores()
                        .stream()
                        .map(DadosConsultaMoradorResumoDTO::new)
                        .toList()
        );
    }
}
