package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.Encomenda;

public record DadosListagemEncomendaDTO(
        Long id,
        String nomePacote,
        String observacao,
        String status,
        String nomeDestinatario,
        String turnoPorteiro
) {
    public DadosListagemEncomendaDTO(Encomenda encomenda) {
        this(
                encomenda.getIdEncomenda(),
                encomenda.getNomePacote(),
                encomenda.getObservacao(),
                encomenda.getStatusEncomenda().toString(),
                encomenda.getUsuario().getPessoa().getNomeCompleto(),
                encomenda.getPorteiro().getTurno().toString()
        );
    }
}