package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.hermes.projeto.backend.entities.security.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    UserDetails findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.pessoa.morador IS NOT NULL AND u.pessoa.ativo = true")
    List<Usuario> findAllMoradores();

    @Query("SELECT u FROM Usuario u WHERE u.pessoa.porteiro IS NOT NULL AND u.pessoa.ativo = true")
    List<Usuario> findAllPorteiros();
}