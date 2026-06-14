package com.hermes.projeto.backend.dto.response;

import com.hermes.projeto.backend.domain.Condominio;

public record DadosConsultaCondominioDTO(

        Long idCondominio,

        String nomeCondominio
) {
    public DadosConsultaCondominioDTO(Condominio condominio){
        this(

                condominio.getId(),
                condominio.getNome_condominio()

        );
    }
}
