package com.hermes.projeto.backend.controller;

import com.hermes.projeto.backend.dto.DadosConsultaMoradorDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarMoradorDTO;
import com.hermes.projeto.backend.services.SindicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/moradores")
public class MoradorController {

    @Autowired
    private SindicoService sindicoService;

    @PostMapping
    public ResponseEntity<DadosConsultaMoradorDTO> registrarMorador(@Valid @RequestBody DadosRegistrarMoradorDTO dados,
                                                             UriComponentsBuilder uriBuilder) {

        var moradorDto = sindicoService.registrarMorador(dados);

        var uri = uriBuilder.path("/moradores/{id}").buildAndExpand(moradorDto.idUsuario()).toUri();

        return ResponseEntity.created(uri).body(moradorDto);
    }

    @GetMapping
    public ResponseEntity<List<DadosConsultaMoradorDTO>> listar() {
        return ResponseEntity.ok(sindicoService.listarTodasMoradores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosConsultaMoradorDTO> detalhar(@PathVariable Long id) {
        var detalhes = sindicoService.buscarMoradorPorId(id);
        return ResponseEntity.ok(detalhes);
    }
}