package com.hermes.projeto.backend.entities;

import java.time.LocalDate;

import com.hermes.projeto.backend.entities.condo.Moradia;
import com.hermes.projeto.backend.entities.security.Papel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "morador")
// Esta anotação vincula a PK desta tabela (id_papel) à PK da tabela Papel
@PrimaryKeyJoinColumn(name = "id_papel") 
public class Morador extends Papel {

    @Column(name = "data_chegada", nullable = false)
    private LocalDate dataChegada;

    @Column(name = "data_saida")
    private LocalDate dataSaida;

    // Conforme discutimos, a foto é uma URL (String)
    @Column(name = "foto_perfil")
    private String urlFoto;

    // Relacionamento com a Moradia (Apartamento/Casa)
    // Muitos moradores podem pertencer a uma moradia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moradia_idMoradia", nullable = false)
    private Moradia moradia;

}
