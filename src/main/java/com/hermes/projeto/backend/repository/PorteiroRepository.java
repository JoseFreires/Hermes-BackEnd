package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.entities.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hermes.projeto.backend.entities.Porteiro;

import java.util.Optional;

@Repository
public interface PorteiroRepository extends JpaRepository<Porteiro, Long>{
    @Query("SELECT u FROM Usuario u WHERE u.pessoa.porteiro.id = :idPorteiro")
    Optional<Usuario> findUsuarioByPorteiroId(@Param("idPorteiro") Long idPorteiro);
}

