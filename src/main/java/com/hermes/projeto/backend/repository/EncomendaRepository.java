package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.Encomenda;
import com.hermes.projeto.backend.enums.StatusEncomenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    @Query("SELECT e FROM Encomenda e JOIN FETCH e.moradorDestinatario JOIN FETCH e.porteiro")
    List<Encomenda> findAll();

    @Query("SELECT e FROM Encomenda e JOIN FETCH e.moradorDestinatario JOIN FETCH e.porteiro WHERE e.statusEncomenda = :status")
    List<Encomenda> findByStatus(@Param("status") StatusEncomenda status);
}