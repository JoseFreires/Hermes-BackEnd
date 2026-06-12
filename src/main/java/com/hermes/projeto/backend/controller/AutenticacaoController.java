package com.hermes.projeto.backend.controller;

import com.hermes.projeto.backend.dto.DadosConsultaLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    TokenService tokenService;

    @PostMapping
    public ResponseEntity logar(@RequestBody @Valid DadosLoginDTO dados){

        var token = new UsernamePasswordAuthenticationToken(dados.username(), dados.senha());

        var authentication = manager.authenticate(token);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        //Transformando o token em String para utilizar no httpOnly
        String tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", tokenJwt)
                .httpOnly(true)       // Proibi o javascript de ler o token
                .secure(false)        // OBS: Aqui coloquei false para testar no localHost, depois mudar para true.
                .path("/")
                .maxAge(2 * 60 * 60)
                .sameSite("Strict")
                .build();

        String roleDoUsuario = usuario.getAuthorities().iterator().next().getAuthority();


        DadosConsultaLoginDTO dadosFrontEnd = new DadosConsultaLoginDTO(
                usuario.getId(),
                usuario.getUsername(),
                roleDoUsuario
        );


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(dadosFrontEnd);

        
    }


    
}