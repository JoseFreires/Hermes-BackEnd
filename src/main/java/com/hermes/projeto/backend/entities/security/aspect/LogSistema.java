package com.hermes.projeto.backend.entities.security.aspect;

import java.time.LocalDateTime;

import com.hermes.projeto.backend.entities.security.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "log_sistema")
@Getter
@Setter
public class LogSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLog")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Usuario_idUsuario", nullable = true)
    private Usuario usuario;

    @Column(name = "acao_realizada")
    private String acao;

    @Column(name = "tabela_alterada")
    private String endpoint;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}
