package com.hermes.projeto.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hermes.projeto.backend.entitie.Porteiro;

@Repository
public interface PorteiroRepository extends JpaRepository<Porteiro, Long>{

}