package com.hermes.projeto.backend.entities.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;    
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;


    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request -> {
            // Autenticação - permitir acesso público
            request.requestMatchers(HttpMethod.POST, "/login").permitAll();
         
            // ENCOMENDAS
            request.requestMatchers(HttpMethod.POST, "/encomendas").hasRole("PORTEIRO");
            request.requestMatchers(HttpMethod.GET, "/encomendas").hasAnyRole("PORTEIRO", "MORADOR", "SINDICO");
            request.requestMatchers(HttpMethod.PUT, "/encomendas/**").hasRole("PORTEIRO");
            request.requestMatchers(HttpMethod.PATCH, "/encomendas/**").hasRole("PORTEIRO");
            request.requestMatchers(HttpMethod.DELETE, "/encomendas/**").hasRole("PORTEIRO");

            // MORADORES
             request.requestMatchers(HttpMethod.POST, "/moradores").hasRole("PORTEIRO");
            request.requestMatchers(HttpMethod.GET, "/moradores").hasAnyRole("PORTEIRO", "ADMIN");
            request.requestMatchers(HttpMethod.GET, "/moradores/**").hasAnyRole("MORADOR", "PORTEIRO", "ADMIN");
            request.requestMatchers(HttpMethod.PUT, "/moradores/**").hasRole("MORADOR");
            request.requestMatchers(HttpMethod.DELETE, "/moradores/**").hasRole("ADMIN");

            // PESSOAS AUTORIZADAS
            request.requestMatchers(HttpMethod.POST, "/pessoas-autorizadas").hasRole("MORADOR");
            request.requestMatchers(HttpMethod.GET, "/pessoas-autorizadas").hasAnyRole("MORADOR", "PORTEIRO", "ADMIN");
            request.requestMatchers(HttpMethod.PUT, "/pessoas-autorizadas/**").hasRole("MORADOR");
            request.requestMatchers(HttpMethod.DELETE, "/pessoas-autorizadas/**").hasRole("MORADOR");

            // CONDOMINIOS
            request.requestMatchers(HttpMethod.GET, "/condominios").hasAnyRole("ADMIN", "PORTEIRO");
            request.requestMatchers(HttpMethod.POST, "/condominios").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.PUT, "/condominios/**").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.DELETE, "/condominios/**").hasRole("ADMIN");

            // BLOCOS
            request.requestMatchers(HttpMethod.GET, "/blocos").hasAnyRole("ADMIN", "PORTEIRO");
            request.requestMatchers(HttpMethod.POST, "/blocos").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.PUT, "/blocos/**").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.DELETE, "/blocos/**").hasRole("ADMIN");

            // MORADIAS
            request.requestMatchers(HttpMethod.GET, "/moradias").hasAnyRole("ADMIN",  "PORTEIRO");
            request.requestMatchers(HttpMethod.POST, "/moradias").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.PUT, "/moradias/**").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.DELETE, "/moradias/**").hasRole("ADMIN");

            // LOGS
            request.requestMatchers(HttpMethod.GET, "/logs").hasAnyRole("ADMIN");
            
            // Resto das requisições requer autenticação
            request.anyRequest().authenticated();
       
        })
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
   
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}