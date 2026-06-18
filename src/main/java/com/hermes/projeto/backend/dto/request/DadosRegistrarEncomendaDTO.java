package com.hermes.projeto.backend.dto.request;

import java.time.LocalDateTime;

import com.hermes.projeto.backend.domain.enums.StatusEncomenda;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistrarEncomendaDTO(

    LocalDateTime dataHoraRecebido, // Pode ser null se o construtor da Entidade gerar o now()

    @NotBlank(message = "O nome do pacote é obrigatório")
    String nomePacote,

    String foto, // Opcional

    @NotNull(message = "O status é obrigatório")
    StatusEncomenda status,

    //Adicionei o email aqui e tirei Morador
    @NotBlank @Email
    String emailDestinatario,

    @Column (name = "id_pessoa_destinatario")
    @NotNull(message = "O ID do destinatário é obrigatório")
    Long idDestinatario,

    String  observacao
) {
}