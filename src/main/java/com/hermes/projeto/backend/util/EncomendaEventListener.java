package com.hermes.projeto.backend.util;

import com.hermes.projeto.backend.domain.Morador;
import com.hermes.projeto.backend.dto.request.DadosEnvioEmailDTO;
import com.hermes.projeto.backend.services.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EncomendaEventListener {

    private final EmailService emailService;

    public EncomendaEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    // AFTER_COMMIT garante que só executa se o banco confirmou
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEncomendaRegistrada(EncomendaRegistradaEvent evento) {
        Morador morador = evento.morador();

        DadosEnvioEmailDTO email = new DadosEnvioEmailDTO(
                morador.getPessoa().getEmail(),
                "Recebemos sua encomenda!",
                "Olá " + morador.getPessoa().getNomeCompleto() + ", sua encomenda chegou!"
        );
        emailService.enviarEmail(email);
    }
}
