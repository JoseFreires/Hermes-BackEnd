package com.hermes.projeto.backend.dto;

public record DadosConsultaPessoaAutorizadaDTO(

        Long idPessoaAutorizada,

        String nome,

        String cpf,

        Long moradorIdMorador,

        String nomeMorador,

        String numeroMoradia
) {
}
