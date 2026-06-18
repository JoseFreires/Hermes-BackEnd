package com.hermes.projeto.backend.dto.request;

import com.hermes.projeto.backend.domain.enums.TipoRetirada;
import jakarta.validation.constraints.NotNull;


public record DadosAtualizarStatusEncomendaDTO(

    @NotNull(message = "Quem retirou deve ser informado")
    TipoRetirada tipoRetirada

){
}