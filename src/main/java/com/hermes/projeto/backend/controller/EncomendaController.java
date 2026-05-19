package com.hermes.projeto.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hermes.projeto.backend.dto.DadosListagemEncomendaDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.services.PortariaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    @Autowired
    private PortariaService portariaService;

    @PostMapping
    public ResponseEntity registrar(@Valid @RequestBody DadosRegistrarEncomendaDTO dados, 
                                    UriComponentsBuilder uriBuilder,
                                    @AuthenticationPrincipal Usuario logado) {
        
        // Agora 'encomenda' já é um DadosListagemEncomendaDTO
        var encomendaDto = portariaService.registrar(dados, logado);
        
        // Em Records, acessamos o id apenas como .id() e não .getIdEncomenda()
        var uri = uriBuilder.path("/encomendas/{id}").buildAndExpand(encomendaDto.id()).toUri();
        
        // Retornamos o DTO que o Service já preparou com carinho (e com a transação aberta)
        return ResponseEntity.created(uri).body(encomendaDto);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemEncomendaDTO>> listar() {
        return ResponseEntity.ok(portariaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var detalhes = portariaService.buscarPorId(id);
        return ResponseEntity.ok(detalhes);
    }
}