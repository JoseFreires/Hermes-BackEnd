package com.hermes.projeto.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoEncomendaDTO(

    @NotBlank(message = "O nome do pacote é obrigatório")
    String nomePacote,

    String observacao,

    @NotBlank(message = "O ID do morador é obrigatório")
    Long moradorId



){}