package com.hermes.projeto.backend.dto;

import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.enums.TurnoPorteiro;

public record DadosConsultaPorteiroDTO(

        Long idPorteiro,

        TurnoPorteiro turno,

        String empresaResponsavel,

        Long pessoaIdPessoa,

        String nomeCompleto,

        String cpf,

        String email,

        String telefone

) {// Construtor
    public DadosConsultaPorteiroDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getPessoa().getPorteiro().getTurno(),
                usuario.getPessoa().getPorteiro().getEmpresaResponsavel(),
                usuario.getPessoa().getId(),
                usuario.getPessoa().getNomeCompleto(),
                usuario.getPessoa().getCpf(),
                usuario.getPessoa().getEmail(),
                usuario.getPessoa().getTelefone()

        );
    }
}
