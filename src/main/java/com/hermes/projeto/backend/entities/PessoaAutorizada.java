package com.hermes.projeto.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor // Adicione isso
@AllArgsConstructor // Adicione isso
@Table(name = "pessoa_autorizada")
@Entity (name = "PessoaAutorizada")
@EqualsAndHashCode (of = "id")
@Getter
@Setter
public class PessoaAutorizada {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idPessoaAutorizada")
        private Long idPessoaAutorizada;
        
        @Column(name = "nome")
        private String nome;
        
        @Column(unique = true)
        private String cpf;

        

}

