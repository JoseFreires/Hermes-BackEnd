package com.hermes.projeto.backend.dto.request;

import com.hermes.projeto.backend.dto.DadosLoginDTO;
import com.hermes.projeto.backend.domain.enums.TurnoPorteiro;

import java.time.LocalDate;

public record DadosAtualizacaoPorteiroDTO(

        TurnoPorteiro turno,

        String empresaResponsavel,

        String nomeCompleto,

        String telefone,

        LocalDate dataNascimento,

        DadosRegistrarPessoaDTO pessoa,

        DadosLoginDTO usuario
) {
}
