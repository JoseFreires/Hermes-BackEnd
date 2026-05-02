package com.hermes.projeto.backend.entities;

import com.hermes.projeto.backend.entities.security.Papel;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity (name = "Porteiro")
@Table(name = "porteiro")
@PrimaryKeyJoinColumn(name = "id_papel")
public class Porteiro extends Papel{


    private String empresaResponsavel;
    private String turno;


}