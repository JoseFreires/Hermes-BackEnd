
package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.projeto.backend.entities.PerfilMorador;

@Repository
public interface MoradorRepository extends JpaRepository<PerfilMorador, Long>{

}

