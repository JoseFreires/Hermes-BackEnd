package com.hermes.projeto.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hermes.projeto.backend.entities.Morador;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long>{

}
