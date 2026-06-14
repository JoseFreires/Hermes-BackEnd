package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.projeto.backend.domain.Bloco;

@Repository
public interface BlocoRepository extends JpaRepository<Bloco, Long>{



}