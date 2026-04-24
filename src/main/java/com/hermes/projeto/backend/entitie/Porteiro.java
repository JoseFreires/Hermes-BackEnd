package com.hermes.projeto.backend.entitie;

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
@PrimaryKeyJoinColumn(name = "idPessoa")
@Entity (name = "Porteiro")
@Table(name = "porteiro")
public class Porteiro extends Pessoa{

    private String crodCracha;
    private String empresaResponsavel;
    private String turno;


}