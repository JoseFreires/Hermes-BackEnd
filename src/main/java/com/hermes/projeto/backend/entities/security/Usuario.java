package com.hermes.projeto.backend.entities.security;

import java.util.List;

import com.hermes.projeto.backend.entities.Pessoa;

public class Usuario {
    
    private String username;
    private String password;
    private Pessoa pessoa;
    private List<Papel> papeis;

}
