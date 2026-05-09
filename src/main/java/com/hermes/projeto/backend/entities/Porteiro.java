package com.hermes.projeto.backend.entities;

import com.hermes.projeto.backend.entities.security.Papel;
import com.hermes.projeto.backend.enums.TurnoPorteiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Porteiro extends Papel {

    @Column(name = "empresa_responsavel", length = 100)
    private String empresaResponsavel;

    @Enumerated(EnumType.STRING) // Ou STRING, dependendo de como salvou no banco
    @Column(name = "turno")
    private TurnoPorteiro turno;

    /* Paralistar todas as encomendas que este porteiro 
       recebeu, pode adicionar o mapeamento inverso aqui:
       
       @OneToMany(mappedBy = "porteiro")
       private List<Encomenda> encomendasRecebidas;
    */
}