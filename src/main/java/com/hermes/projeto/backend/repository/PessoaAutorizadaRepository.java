package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.PessoaAutorizada;
import com.hermes.projeto.backend.entities.security.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaAutorizadaRepository extends JpaRepository<PessoaAutorizada, Long> {
}
