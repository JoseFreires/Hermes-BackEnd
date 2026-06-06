package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.entities.security.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
