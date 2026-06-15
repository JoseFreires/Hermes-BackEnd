package com.hermes.projeto.backend.dto.response;


import java.time.LocalDate;

import com.hermes.projeto.backend.domain.Morador;
import com.hermes.projeto.backend.security.Usuario;

public record DadosConsultaMoradorDTO(
        Long idMorador,
        Long idPessoa,
        String nome,
        String email,
        String numeroApartamento,
        String bloco,
        String cpf,
        LocalDate dataChegada,
        LocalDate nascimento,
        String foto,
        String telefone
) {
    public DadosConsultaMoradorDTO(Usuario usuario) {
        this(
                usuario.getPessoa().getMorador().getId(),
                usuario.getPessoa().getId(),
                usuario.getPessoa().getNomeCompleto(),
                usuario.getPessoa().getEmail(),
                usuario.getPessoa().getMorador().getMoradia().getNumero(),
                usuario.getPessoa().getMorador().getMoradia().getBloco().getNome_torre(),
                usuario.getPessoa().getCpf(),
                usuario.getPessoa().getMorador().getDataChegada(),
                usuario.getPessoa().getDataNascimento(),
                usuario.getPessoa().getMorador().getUrlFoto(),
                usuario.getPessoa().getTelefone()

        );

    }

}