package com.hermes.projeto.backend.entities.security.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.entities.security.aspect.LogSistema;
import com.hermes.projeto.backend.repository.LogSistemaRepository;

@Service
public class LogSistemaService {

    @Autowired
    private LogSistemaRepository repository;

    public void salvar(Usuario usuario, String acao, String endpoint) {

        LogSistema log = new LogSistema();
        log.setUsuario(usuario);
        log.setAcao(acao);
        log.setEndpoint(endpoint);
        log.setDataHora(LocalDateTime.now());

        repository.save(log);
    }
}