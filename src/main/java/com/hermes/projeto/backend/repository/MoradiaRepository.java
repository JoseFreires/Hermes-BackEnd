package com.hermes.projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.projeto.backend.domain.Moradia;

@Repository
public interface MoradiaRepository extends JpaRepository<Moradia, Long>{

}