package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.Encomenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    @Query("SELECT e FROM Encomenda e JOIN FETCH e.morador JOIN FETCH e.porteiro")
    Page<Encomenda> findAll(Pageable pageable);
}