package com.hermes.projeto.backend.dto.request;

import com.hermes.projeto.backend.dto.DadosLoginDTO;

public record DadosRegistrarSindicoDTO(
        DadosRegistrarPessoaDTO pessoa,
        DadosLoginDTO login)
{}
