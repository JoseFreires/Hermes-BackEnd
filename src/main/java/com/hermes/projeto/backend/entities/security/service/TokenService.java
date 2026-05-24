package com.hermes.projeto.backend.entities.security.service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hermes.projeto.backend.entities.security.Usuario;


@Service
public class TokenService{
    
    @Value("${api.security.tokenJWT.segredo}")
    private  String segredo;

    public String gerarToken(Usuario usuario){
        try {
            
            Algorithm  algorithm = Algorithm.HMAC256(segredo.trim());
          
            List<String> roles = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

            return JWT.create()
                .withIssuer("Hermes")
                .withSubject(usuario.getUsername()) 
                .withClaim("roles", roles)              // Adiciona a lista de Strings ["ROLE_PORTEIRO", "ROLE_MORADOR"]
                .withExpiresAt(Expiracao())
                .sign(algorithm);

                
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token", exception);
        }

       

    }

    private Instant Expiracao(){

    return OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"))
                     .plusHours(2)
                     .toInstant();
    }


    public String getSubject(String tokenJwt){

        try {
            Algorithm  algorithm = Algorithm.HMAC256(segredo);
            return   JWT.require(algorithm)
                .withIssuer("Hermes")
                .build()
                .verify(tokenJwt)
                .getSubject();
                
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado", exception);
        }
    }
    
}