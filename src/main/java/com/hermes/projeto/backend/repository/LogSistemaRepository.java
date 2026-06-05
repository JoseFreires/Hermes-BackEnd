package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hermes.projeto.backend.entities.security.aspect.LogSistema;


public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {
}