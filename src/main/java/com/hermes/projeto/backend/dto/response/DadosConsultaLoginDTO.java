package com.hermes.projeto.backend.dto.response;

public record DadosConsultaLoginDTO(
        Long id,
        String username,
        String nome,
        String role
) {
}
