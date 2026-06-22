package com.hermes.projeto.backend.services;

import com.hermes.projeto.backend.dto.request.DadosEnvioEmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void enviarEmail(DadosEnvioEmailDTO dados) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(remetente);
            mensagem.setTo(dados.destinatario());
            mensagem.setSubject(dados.assunto());
            mensagem.setText(dados.corpo());
            mailSender.send(mensagem);
            logger.info("Email enviado para {}", dados.destinatario());
        } catch (Exception e) {
            logger.error("Falha ao enviar email para {}: {}", dados.destinatario(), e.getMessage());
        }
    }

    @Override
    @Async
    public void enviarEmailHtml(String destinatario, String assunto, String template, Object contexto) {
        try {
            Context ctx = new Context();
            ctx.setVariable("dados", contexto);
            String htmlBody = templateEngine.process(template, ctx);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
            logger.info("Email HTML enviado para {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Falha ao enviar email HTML para {}: {}", destinatario, e.getMessage());
        }
    }
}