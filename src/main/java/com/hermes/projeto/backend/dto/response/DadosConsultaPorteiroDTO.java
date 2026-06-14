package com.hermes.projeto.backend.dto.response;

import com.hermes.projeto.backend.security.Usuario;
import com.hermes.projeto.backend.domain.enums.TurnoPorteiro;

public record DadosConsultaPorteiroDTO(

        Long idPorteiro,

        Long idUsuario,

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
                usuario.getPessoa().getPorteiro().getId(),
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
