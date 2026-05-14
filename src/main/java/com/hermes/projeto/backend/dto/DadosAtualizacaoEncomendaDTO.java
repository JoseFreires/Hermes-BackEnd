package com.hermes.projeto.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoEncomendaDTO(

    @NotBlank(message = "O nome do pacote é obrigatório")
    String nomePacote,

    String observacao,

    //Incluindo email para ligação se atualizar
    @NotBlank(message = "O email do usuario é obrigatório")
    String emailDestinatario

){}