package com.hermes.projeto.backend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor // Adicione isso
@AllArgsConstructor // Adicione isso
@Table(name = "pessoa")
@Entity (name = "Pessoa")
@EqualsAndHashCode (of = "id")
@Getter
@Setter
public abstract class Pessoa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomeCompleto;
    
    @Column(unique = true)
    private String cpf;


    private Boolean ativo;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    private String telefone;
    private String email;
    

}
