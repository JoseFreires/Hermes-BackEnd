package com.hermes.projeto.backend.dto.response;

import java.util.List;

public record DadosConsultaMoradorEncomendasDTO (
        List<DadosConsultaEncomendaDTO> encomendas
) {

}
