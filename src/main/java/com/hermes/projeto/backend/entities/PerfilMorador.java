package com.hermes.projeto.backend.entities;

import java.time.LocalDate;
import java.util.Date;

import com.hermes.projeto.backend.dto.DadosRegistrarMoradorDTO;
import com.hermes.projeto.backend.entities.condo.Moradia;
import com.hermes.projeto.backend.entities.security.Papel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil_morador")
public class PerfilMorador {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerfilMorador")
    private Long id;

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

    @OneToOne
    @JoinColumn(name = "Pessoa_idPessoa", nullable = false, unique = true)
    private Pessoa pessoa;


    public PerfilMorador(DadosRegistrarMoradorDTO dados, Moradia moradia) {
        this.dataChegada = dados.dataChegada();
        this.urlFoto = dados.fotoPerfil();
        this.moradia = moradia;
    }

    public PerfilMorador(){
        
    }

}
