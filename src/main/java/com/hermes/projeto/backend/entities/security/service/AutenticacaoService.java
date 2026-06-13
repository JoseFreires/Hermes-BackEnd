package com.hermes.projeto.backend.entities.security.service;

import com.hermes.projeto.backend.repository.ContaAdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hermes.projeto.backend.repository.UsuarioRepository;


@Service
public class AutenticacaoService implements UserDetailsService{

    @Autowired
    UsuarioRepository repository;

    @Autowired
    ContaAdmRepository repositoryContaAdm;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(repository.findByUsername(username) != null){
            return repository.findByUsername(username);
        } else {
            return repositoryContaAdm.findByUsername(username);
        }


    }


}