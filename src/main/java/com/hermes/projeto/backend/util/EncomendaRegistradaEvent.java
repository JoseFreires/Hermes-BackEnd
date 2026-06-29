package com.hermes.projeto.backend.util;


public record EncomendaRegistradaEvent(String nomeDestinatario,
                                       String emailDestinatario,
                                       String nomePacote,
                                       String nomePorteiro,
                                       String dataChegada) {
}
