package com.hermes.projeto.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.projeto.backend.dto.DadosLoginDTO;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.entities.security.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    TokenService TokenService;

    @PostMapping
    public ResponseEntity logar(@RequestBody @Valid DadosLoginDTO dados){

        var token = new UsernamePasswordAuthenticationToken(dados.username(), dados.senha());

        var authentication = manager.authenticate(token);

        return ResponseEntity.ok(TokenService.gerarToken( (Usuario) authentication.getPrincipal()));

        
    }


    
}