package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.PerfilMorador;
import com.hermes.projeto.backend.entities.security.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PapelRepository extends JpaRepository<Papel, Long> {
    Optional<Papel> findByNomePapel(String nomePapel);
}
