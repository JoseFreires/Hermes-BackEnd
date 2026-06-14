package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hermes.projeto.backend.infra.aop.LogSistema;


public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {
}