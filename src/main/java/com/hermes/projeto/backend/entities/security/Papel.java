package com.hermes.projeto.backend.entities.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "papel")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPapel")
    private Long id;

    @Column(name = "nome_papel", nullable = false, unique = true)
    private String nomePapel;

    /* Para saber a qual usuário este papel pertence 
       diretamente pelo objeto Papel, você pode mapear o lado inverso 
       do relacionamento ManyToMany ou OneToOne aqui futuramente.
    */
}
