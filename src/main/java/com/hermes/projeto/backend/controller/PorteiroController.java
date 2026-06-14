package com.hermes.projeto.backend.controller;

import com.hermes.projeto.backend.dto.DadosAtualizacaoMoradorDTO;
import com.hermes.projeto.backend.dto.DadosAtualizacaoPorteiroDTO;
import com.hermes.projeto.backend.dto.DadosConsultaPorteiroDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarPorteiroDTO;
import com.hermes.projeto.backend.services.SindicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/porteiros")
public class PorteiroController {

    @Autowired
    private SindicoService sindicoService;

    // Busca todos os porteiros ativos
    @GetMapping
    public ResponseEntity<List<DadosConsultaPorteiroDTO>> listarPorteiros() {
        return ResponseEntity.ok(sindicoService.listarTodosPorteiros());
    }

    // Busca um porteiro pelo seu id
    @GetMapping("/{id}")
    public ResponseEntity<DadosConsultaPorteiroDTO> encontrarPorteiroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sindicoService.buscarPorteiroPorId(id));
    }

    // Cria um novo porteiro
    @PostMapping
    public ResponseEntity<DadosConsultaPorteiroDTO> registrarPorteiro(@Valid @RequestBody DadosRegistrarPorteiroDTO dados,
                                                                     UriComponentsBuilder uriBuilder) {

        var porteiroDTO = sindicoService.registrarPorteiro(dados);

        var uri = uriBuilder.path("/porteiros/{id}").buildAndExpand(porteiroDTO.idUsuario()).toUri();

        return ResponseEntity.created(uri).body(porteiroDTO);
    }

    // Atualiza porteiro por Id
    @PutMapping("/{id}")
    public ResponseEntity<Void> editarPorteiro(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizacaoPorteiroDTO dados) {

        sindicoService.editarPorteiro(id, dados);
        return ResponseEntity.noContent().build();
    }

    // Deletar porteiro por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorteiroPorId(@PathVariable Long id) {
        sindicoService.desativarPorteiro(id);
        return ResponseEntity.noContent().build();
    }

}
