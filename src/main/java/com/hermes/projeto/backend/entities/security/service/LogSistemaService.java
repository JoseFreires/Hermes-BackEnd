package com.hermes.projeto.backend.entities.security.service;

import java.time.LocalDateTime;

import com.hermes.projeto.backend.entities.svc.ContaAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.entities.security.aspect.LogSistema;
import com.hermes.projeto.backend.repository.LogSistemaRepository;

@Service
public class LogSistemaService {

    private final LogSistemaRepository repository;

    public LogSistemaService(LogSistemaRepository repository) {
        this.repository = repository;
    }

    public void salvarPorUsuario(Usuario usuario, String metodo, String endpoint) {
        LogSistema log = new LogSistema();
        log.setAcaoRealizada(metodo);
        log.setTabelaAlterada(endpoint);
        log.setDataHora(LocalDateTime.now());
        log.setUsuario(usuario);
        log.setContaAdm(null);
        repository.save(log);
    }

    public void salvarPorContaAdm(ContaAdm contaAdm, String metodo, String endpoint) {
        LogSistema log = new LogSistema();
        log.setAcaoRealizada(metodo);
        log.setTabelaAlterada(endpoint);
        log.setDataHora(LocalDateTime.now());
        log.setUsuario(null);
        log.setContaAdm(contaAdm);
        repository.save(log);
    }
}