package com.hermes.projeto.backend.dto;

import java.time.LocalDateTime;

public record DadosConsultaLogDTO(

        Long idLog,

        String acaoRealizada,

        String tabelaAlterada,

        LocalDateTime dataHora,

        Long usuarioIdUsuario,

        Long contaAdmIdConta,

        String username,

        String nomeContaAdm
) {
}
