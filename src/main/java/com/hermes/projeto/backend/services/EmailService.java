package com.hermes.projeto.backend.services;

import com.hermes.projeto.backend.dto.request.DadosEnvioEmailDTO;

public interface EmailService {
    void enviarEmail(DadosEnvioEmailDTO dados);
}
