package com.hermes.projeto.backend.entities.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "papel")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Papel {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPapel;
    
}
