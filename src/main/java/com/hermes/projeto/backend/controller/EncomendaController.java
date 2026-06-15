package com.hermes.projeto.backend.controller;

import java.net.URI;
import java.util.List;

import com.hermes.projeto.backend.dto.request.DadosAtualizacaoEncomendaDTO;
import com.hermes.projeto.backend.dto.request.DadosAtualizarStatusEncomendaDTO;
import com.hermes.projeto.backend.domain.enums.StatusEncomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.hermes.projeto.backend.dto.response.DadosConsultaEncomendaDTO;
import com.hermes.projeto.backend.dto.request.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.security.Usuario;
import com.hermes.projeto.backend.services.PortariaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    @Autowired
    private PortariaService portariaService;

    @GetMapping
    public ResponseEntity<List<DadosConsultaEncomendaDTO>> listarEncomendas(
            @RequestParam(required = false) StatusEncomenda status) {

        return ResponseEntity.ok(portariaService.listarEncomendas(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity encontrarEncomendaPorId(@PathVariable Long id) {
        var detalhes = portariaService.buscarEncomendaPorId(id);
        return ResponseEntity.ok(detalhes);
    }

    @PostMapping
    public ResponseEntity<DadosConsultaEncomendaDTO> registrarEncomenda(
            @Valid @RequestBody DadosRegistrarEncomendaDTO dados,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Usuario logado) {

        DadosConsultaEncomendaDTO encomendaDto = portariaService.registrarEncomenda(dados, logado);
        URI uri = uriBuilder.path("/encomendas/{id}").buildAndExpand(encomendaDto.idEncomenda()).toUri();

        return ResponseEntity.created(uri).body(encomendaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editarEncomenda(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizacaoEncomendaDTO dados) {

        portariaService.editarEncomenda(id, dados);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/entrega")
    public ResponseEntity<Void> registrarEntrega(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizarStatusEncomendaDTO dados) {

        portariaService.registrarEntregaEncomenda(id, dados);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEncomendaPorId(@PathVariable Long id) {
        portariaService.deletarEncomendaPorId(id);
        return ResponseEntity.noContent().build();
    }

}