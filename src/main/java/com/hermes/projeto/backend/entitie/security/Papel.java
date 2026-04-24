/*

package com.hermes.projeto.backend.entitie.security;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Papel")
@Table(name = "papel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPapel")
public class Papel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPapel") // Garante o mapeamento com o banco
    private Integer idPapel;

    @Column(name = "nome_papel", nullable = false, unique = true) // Garante o mapeamento com o banco
    private String nomePapel; 

    @Override
    public String getAuthority() {
        return this.nomePapel;
        }
    
}

*/