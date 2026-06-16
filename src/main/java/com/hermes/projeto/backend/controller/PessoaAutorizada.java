package com.hermes.projeto.backend.controller;

import com.hermes.projeto.backend.dto.request.DadosAtualizacaoPessoaAutorizadaDTO;
import com.hermes.projeto.backend.dto.request.DadosRegistrarPessoaAutorizadaDTO;
import com.hermes.projeto.backend.dto.response.DadosConsultaPessoaAutorizadaDTO;
import com.hermes.projeto.backend.services.MoradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pessoasAutorizadas")
public class PessoaAutorizada {

    @Autowired
    MoradorService moradorService;

    // Busca todos as pessoas autorizadas ativas
    @GetMapping
    public ResponseEntity<List<DadosConsultaPessoaAutorizadaDTO>> listarPessoasAutorizadas() {
        return ResponseEntity.ok(moradorService.listarTodasPessoasAutorizadas());
    }

    // Busca uma pessoa autorizada pelo seu id
    @GetMapping("/{id}")
    public ResponseEntity<DadosConsultaPessoaAutorizadaDTO> encontrarPessoaAutorizadaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(moradorService.buscarPessoaAutorizadaPorId(id));
    }


    // Registra uma pessoa autorizada
    @PostMapping
    public ResponseEntity<DadosConsultaPessoaAutorizadaDTO> registrarPessoaAutorizada(
            @Valid @RequestBody DadosRegistrarPessoaAutorizadaDTO dados,
            UriComponentsBuilder uriBuilder) {

        var pessoaAutorizadaDTO = moradorService.registrarPessoaAutorizada(dados);

        var uri = uriBuilder.path("/pessoasAutorizadas/{id}").buildAndExpand(pessoaAutorizadaDTO.idPessoaAutorizada()).toUri();

        return ResponseEntity.created(uri).body(pessoaAutorizadaDTO);
    }

    // Atualiza a pessoa autorizada
    @PutMapping("/{id}")
    public ResponseEntity<Void> editarPessoaAutorizada(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizacaoPessoaAutorizadaDTO dados) {

        moradorService.editarPessoaAutorizada(dados, id);
        return ResponseEntity.noContent().build();
    }

}
