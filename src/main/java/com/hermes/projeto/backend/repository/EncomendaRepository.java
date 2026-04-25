package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.projeto.backend.entities.Encomenda;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, Long>{

}