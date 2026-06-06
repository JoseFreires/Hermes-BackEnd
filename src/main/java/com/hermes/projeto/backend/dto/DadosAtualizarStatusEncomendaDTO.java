package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.enums.StatusEncomenda;

import jakarta.validation.constraints.NotNull;


public record DadosAtualizarStatusEncomendaDTO(
    
    @NotNull(message = "O novo status deve ser informado")
    StatusEncomenda status
){
}