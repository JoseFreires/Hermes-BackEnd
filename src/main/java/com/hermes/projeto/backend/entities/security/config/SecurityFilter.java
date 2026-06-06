package com.hermes.projeto.backend.entities.security.config;

import java.io.IOException;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hermes.projeto.backend.entities.security.service.TokenService;
import com.hermes.projeto.backend.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)         
            throws IOException, ServletException{
                
                var tokenJwt = recuperarToken(request);

               if (tokenJwt != null){
                 var subject = tokenService.getSubject(tokenJwt);

                 var usuario = repository.findByUsername(subject);

                 var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

                 SecurityContextHolder.getContext().setAuthentication(authentication);
                 
            }
              

            filterChain.doFilter(request, response);
        }


    private String recuperarToken(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {

                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}