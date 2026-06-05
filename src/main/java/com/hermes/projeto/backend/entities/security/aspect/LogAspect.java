package com.hermes.projeto.backend.entities.security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.entities.security.service.LogSistemaService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Autowired
    private LogSistemaService logService;

    @Autowired
    private HttpServletRequest request;

    @Before("execution(* com.hermes..controller..*(..))")
    public void registrarLog(JoinPoint joinPoint) {

        String path = request.getRequestURI();

        // Ignora login
        if (path.equals("/login")) {
            return;
        }

        var auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = null;

        if (auth != null
            && auth.isAuthenticated()
            && auth.getPrincipal() instanceof Usuario) {

            usuario = (Usuario) auth.getPrincipal();
            
            boolean ehMorador = usuario.getAuthorities()
                    .stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_MORADOR"));

                if (ehMorador) {
                    return;
                }

        }

        String metodo = request.getMethod();
        String endpoint = path;

        logService.salvar(usuario, metodo, endpoint);
    }
}