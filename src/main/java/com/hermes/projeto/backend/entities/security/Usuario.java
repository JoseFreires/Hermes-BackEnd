package com.hermes.projeto.backend.entities.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.hermes.projeto.backend.dto.DadosLoginDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hermes.projeto.backend.entities.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Usuario")
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String senha;

    // Relacionamento 1:1 com Pessoa (Um usuário é uma pessoa específica)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pessoa_idPessoa", nullable = false)
    private Pessoa pessoa;

    // Relacionamento Many-to-Many com Papel
    // Isso cria a tabela de ligação 'usuario_papel' automaticamente
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_papel",
        joinColumns = @JoinColumn(name = "Usuario_idUsuario"),
        inverseJoinColumns = @JoinColumn(name = "Papel_idPapel")
    )


    private Set<Papel> papeis = new HashSet<>();

    /* Usamos Set em vez de List para evitar papéis duplicados 
       e FetchType.EAGER para que os papéis venham carregados no login.
    */

    public Usuario(DadosLoginDTO dados, String senhaCriptografada) {
        this.username = dados.username();
        this.senha = senhaCriptografada;
    }

    public Usuario() {

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
}
