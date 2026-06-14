package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.domain.PessoaAutorizada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaAutorizadaRepository extends JpaRepository<PessoaAutorizada, Long> {
}
