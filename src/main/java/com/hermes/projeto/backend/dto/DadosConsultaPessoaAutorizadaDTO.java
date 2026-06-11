package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.Morador;
import com.hermes.projeto.backend.entities.PessoaAutorizada;

public record DadosConsultaPessoaAutorizadaDTO(

        Long idPessoaAutorizada,

        String nome,

        String cpf,

        Long moradorIdMorador,

        String nomeMorador,

        String numeroMoradia
) {
    public DadosConsultaPessoaAutorizadaDTO(PessoaAutorizada pessoaAutorizada){
        this(

                pessoaAutorizada.getIdPessoaAutorizada(),
                pessoaAutorizada.getNome(),
                pessoaAutorizada.getCpf(),
                pessoaAutorizada.getMorador().getId(),
                pessoaAutorizada.getMorador().getPessoa().getNomeCompleto(),
                pessoaAutorizada.getMorador().getMoradia().getNumero()


        );
    }
}
