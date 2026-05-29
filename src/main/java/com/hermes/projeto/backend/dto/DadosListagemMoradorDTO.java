package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.entities.condo.Moradia;
import com.hermes.projeto.backend.entities.security.Usuario;

import java.time.LocalDate;
import java.util.Date;

public record DadosListagemMoradorDTO(
        Long   idUsuario,
        String nome,
        String email,
        String numeroApartamento,
        String cpf,
        LocalDate dataChegada,
        LocalDate nascimento,
        String foto,
        String telefone
) {
    // Construtor
    public DadosListagemMoradorDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getPessoa().getNomeCompleto(),
                usuario.getPessoa().getEmail(),
                usuario.getPessoa().getPerfilMorador().getMoradia().getNumero(),
                usuario.getPessoa().getCpf(),
                usuario.getPessoa().getPerfilMorador().getDataChegada(),
                usuario.getPessoa().getDataNascimento(),
                usuario.getPessoa().getPerfilMorador().getUrlFoto(),
                usuario.getPessoa().getTelefone()

        );
    }

}