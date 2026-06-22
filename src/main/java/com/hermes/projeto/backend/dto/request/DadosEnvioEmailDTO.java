package com.hermes.projeto.backend.dto.request;

public record DadosEnvioEmailDTO(
        String destinatario,
        String assunto,
        String corpo) {
}

