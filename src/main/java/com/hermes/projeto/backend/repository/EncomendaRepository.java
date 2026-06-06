package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.Encomenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    @Query("SELECT e FROM Encomenda e JOIN FETCH e.moradorDestinatario JOIN FETCH e.porteiro")
    List<Encomenda> findAll();
}