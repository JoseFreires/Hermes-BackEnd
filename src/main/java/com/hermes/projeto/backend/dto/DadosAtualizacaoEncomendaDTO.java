package com.hermes.projeto.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoEncomendaDTO(

    String nomePacote,

    String observacao,

    Long idDestinatario

){}