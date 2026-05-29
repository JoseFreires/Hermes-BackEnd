package com.hermes.projeto.backend.entities;

import java.time.LocalDate;
import java.util.Date;

import com.hermes.projeto.backend.dto.DadosPessoaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import  com.hermes.projeto.backend.entities.PerfilMorador;
import org.springframework.cglib.core.Local;


@NoArgsConstructor // Adicione isso
@AllArgsConstructor // Adicione isso
@Table(name = "pessoa")
@Entity (name = "Pessoa")
@EqualsAndHashCode (of = "id")
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPessoa")
    private Long id;

    @Column(name = "nome_completo")
    private String nomeCompleto;


    @Column(unique = true)
    private String cpf;

    private Boolean ativo;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private LocalDate dataNascimento;
    
    private String telefone;
    private String email;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerfilMorador perfilMorador;


    public Pessoa(DadosPessoaDTO dados) {
        this.nomeCompleto = dados.nomeCompleto();
        this.cpf = dados.cpf();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.dataNascimento = dados.dataNascimento();
        this.ativo = true;
    }


}
