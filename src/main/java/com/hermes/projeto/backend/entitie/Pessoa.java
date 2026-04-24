package com.hermes.projeto.backend.entitie;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.JOINED)
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

/*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pessoa_papel", // Nome da tabela que você criou no MySQL
        joinColumns = @JoinColumn(name = "Pessoa_idPessoa"),
        inverseJoinColumns = @JoinColumn(name = "Papel_idPapel")
    )
    private List<Papel> papeis;

    public Collection<? extends GrantedAuthority> getPapeis() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

*/


}
