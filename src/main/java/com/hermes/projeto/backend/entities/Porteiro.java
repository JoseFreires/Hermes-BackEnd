package com.hermes.projeto.backend.entities;

import com.hermes.projeto.backend.dto.DadosRegistrarPorteiroDTO;
import com.hermes.projeto.backend.entities.security.Papel;
import com.hermes.projeto.backend.enums.TurnoPorteiro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity (name = "Porteiro")
@Table(name = "perfil_porteiro")

public class Porteiro{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPerfilPorteiro")
    private Long id;

    @Column(name = "empresa_responsavel", length = 100)
    private String empresaResponsavel;

    @Enumerated(EnumType.STRING) // Ou STRING, dependendo de como salvou no banco
    @Column(name = "turno")
    private TurnoPorteiro turno;

    @OneToOne
    @JoinColumn(name = "Pessoa_idPessoa", nullable = false, unique = true)
    private Pessoa pessoa;

    public Porteiro(DadosRegistrarPorteiroDTO dados) {

        this.empresaResponsavel = dados.empresaResponsavel();
        this.turno = dados.turno();


    }

    /* Paralistar todas as encomendas que este porteiro
       recebeu, pode adicionar o mapeamento inverso aqui:
       
       @OneToMany(mappedBy = "porteiro")
       private List<Encomenda> encomendasRecebidas;
    */
}