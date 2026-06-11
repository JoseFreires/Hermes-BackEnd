package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.condo.Condominio;

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
