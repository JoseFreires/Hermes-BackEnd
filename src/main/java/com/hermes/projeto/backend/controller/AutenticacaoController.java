package com.hermes.projeto.backend.controller;

import com.hermes.projeto.backend.dto.DadosConsultaLoginDTO;
import com.hermes.projeto.backend.entities.svc.ContaAdm;
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
    public ResponseEntity<DadosConsultaLoginDTO> logar(@RequestBody @Valid DadosLoginDTO dados) {

        var token = new UsernamePasswordAuthenticationToken(dados.username(), dados.senha());
        var authentication = manager.authenticate(token);
        var principal = authentication.getPrincipal();

        String tokenJwt;
        DadosConsultaLoginDTO dadosFrontEnd;

        if (principal instanceof Usuario usuario) {
            tokenJwt = tokenService.gerarTokenUsuario(usuario);

            String role = usuario.getAuthorities().iterator().next().getAuthority();

            dadosFrontEnd = new DadosConsultaLoginDTO(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getPessoa().getNomeCompleto(),
                    role
            );

        } else if (principal instanceof ContaAdm contaAdm) {
            tokenJwt = tokenService.gerarTokenContaAdm(contaAdm);

            String role = contaAdm.getAuthorities().iterator().next().getAuthority();

            dadosFrontEnd = new DadosConsultaLoginDTO(
                    contaAdm.getIdContaAdm(),
                    contaAdm.getUsername(),
                    contaAdm.getNomeConta(),
                    role
            );

        } else {
            throw new ClassCastException("Tipo de usuário não reconhecido: " + principal.getClass());
        }

        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", tokenJwt)
                .httpOnly(true)
                .secure(false) // OBS: mudar para true em produção
                .path("/")
                .maxAge(2 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(dadosFrontEnd);
    }

}
