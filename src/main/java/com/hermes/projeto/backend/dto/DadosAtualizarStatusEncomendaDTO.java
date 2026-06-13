package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.enums.StatusEncomenda;

import com.hermes.projeto.backend.enums.TipoRetirada;
import jakarta.validation.constraints.NotNull;


public record DadosAtualizarStatusEncomendaDTO(

    @NotNull(message = "Quem retirou deve ser informado")
    TipoRetirada tipoRetirada

){
}