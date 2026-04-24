package com.hermes.projeto.backend.dto;

import java.time.LocalDateTime;

import com.hermes.projeto.backend.enums.StatusEncomenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistrarEncomendaDTO(

    LocalDateTime dataHoraRecebido, // Pode ser null se o construtor da Entidade gerar o now()

    @NotBlank(message = "O nome do pacote é obrigatório")
    String nomePacote,

    String foto, // Opcional

  

    @NotNull(message = "O status é obrigatório")
    StatusEncomenda status,

    String nomeRecepetor, // Opcional (já que temos o ID do morador)

    @NotNull(message = "O ID do morador é obrigatório")
    Long idMorador,

    @NotNull(message = "O ID do porteiro é obrigatório")
    Long idPorteiro,

    String token // Opcional
) {
}