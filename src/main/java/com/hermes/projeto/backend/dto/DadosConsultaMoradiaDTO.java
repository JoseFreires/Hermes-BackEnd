package com.hermes.projeto.backend.dto;

public record DadosConsultaMoradiaDTO(

        Long idMoradia,

        String numero,

        Long blocoIdBloco,

        String nomeBloco,

        Long condominioId,

        String nomeCondominio
) {
}
