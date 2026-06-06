package com.hermes.projeto.backend.dto;

public record DadosConsultaPorteiroDTO(

        Long idPorteiro,

        String turno,

        String empresaResponsavel,

        Long pessoaIdPessoa,

        String nomeCompleto,

        String cpf,

        String email,

        String telefone
) {
}
