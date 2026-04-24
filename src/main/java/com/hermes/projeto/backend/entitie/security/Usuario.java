/*
package com.hermes.projeto.backend.entitie.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hermes.projeto.backend.entitie.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of ="idUsuario")
public class Usuario implements UserDetails{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    private String username;
    private String senha;

    @OneToOne
    @JoinColumn(name = "Pessoa_idPessoa")
    private Pessoa pessoa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Puxa as roles (ROLE_PORTEIRO, etc) que mapeamos na Pessoa
        return pessoa.getPapeis();
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return senha;
    }

    public String setSenha(String senha){
        return this.senha = senha;
    }

    @Override 
    public boolean isAccountNonExpired() { 

        return true; 

    } 

    @Override 
    public boolean isEnabled() {    

        return true; 

    } 

    @Override 
    public boolean isCredentialsNonExpired() { 

        return true; 

    } 


}
*/