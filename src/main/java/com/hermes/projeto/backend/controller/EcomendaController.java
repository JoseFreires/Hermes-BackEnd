package com.hermes.projeto.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entitie.Encomenda;
import com.hermes.projeto.backend.repository.EncomendaRepository;
import com.hermes.projeto.backend.repository.MoradorRepository;
import com.hermes.projeto.backend.repository.PorteiroRepository;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/encomendas")
public class EcomendaController{

    @Autowired
    MoradorRepository moradorRepository;

    @Autowired
    PorteiroRepository porteiroRepository; 


    @Autowired
    EncomendaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrarEncomenda( @Valid @RequestBody DadosRegistrarEncomendaDTO dados, UriComponentsBuilder uriBuilder){
        
        var morador = moradorRepository.getReferenceById(dados.idMorador());
        var porteiro = porteiroRepository.getReferenceById(dados.idPorteiro());

        var encomenda = new Encomenda(dados, morador, porteiro);
        repository.save(encomenda);

        var uri = uriBuilder.path("/encomendas/{id}").buildAndExpand(encomenda.getidEncomenda()).toUri();
        return ResponseEntity.created(uri).body(dados);
    }


    

}