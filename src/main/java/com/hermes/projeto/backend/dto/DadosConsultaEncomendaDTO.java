package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.Encomenda;

public record DadosConsultaEncomendaDTO(
        Long id,
        String nomePacote,
        String observacao,
        String status,
        String nomeMorador,    // Antes: nomeDestinatario
        String nomePorteiro, // Antes: turnoPorteiro (não existe mais turno na tabela usuario)
        String numeroApartamento,
        String emailDestinatario
){
    // Construtor para converter a Entidade Encomenda direto para o DTO
    public DadosConsultaEncomendaDTO(Encomenda encomenda) {
        this(
            encomenda.getIdEncomenda(),
            encomenda.getNomePacote(),
            encomenda.getObservacao(),
            encomenda.getStatusEncomenda().toString(),
            encomenda.getMoradorDestinatario().getNomeCompleto(), // Pega o nome do Usuario (Morador)
            encomenda.getPorteiro().getPessoa().getNomeCompleto(),// Pega o nome do Usuario (Porteiro)
            encomenda.getMoradorDestinatario().getPerfilMorador().getMoradia().getNumero(),
            encomenda.getMoradorDestinatario().getEmail()
        );
    }
}