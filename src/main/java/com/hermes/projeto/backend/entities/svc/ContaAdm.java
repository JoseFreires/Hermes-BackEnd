package com.hermes.projeto.backend.entities.svc;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hermes.projeto.backend.dto.DadosLoginDTO;
import com.hermes.projeto.backend.entities.security.Papel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity(name = "ContaAdm")
@Table(name = "conta_adm")
public class ContaAdm implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConta")
    private Long idContaAdm;

    @Column(name = "nome_conta", nullable = false)
    private String nomeConta;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String senha;

    private Boolean ativo;

    private Set<Papel> papeis = new HashSet<>();

   
    public ContaAdm(DadosLoginDTO dados, String senhaCriptografada) {
        this.username = dados.username();
        this.senha = senhaCriptografada;
    }

    public ContaAdm() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return papeis.stream()
            .map(papel -> new SimpleGrantedAuthority(papel.getNomePapel()))
            .toList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
