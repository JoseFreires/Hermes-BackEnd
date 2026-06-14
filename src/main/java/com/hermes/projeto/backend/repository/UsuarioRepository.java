package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.hermes.projeto.backend.entities.security.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("SELECT u FROM Usuario u JOIN FETCH u.pessoa JOIN FETCH u.papel WHERE u.username = :username")
    UserDetails findByUsername(@Param("username") String username);


}